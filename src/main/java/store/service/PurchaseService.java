package store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.model.Promotion;
import store.model.PurchasedProduct;
import store.model.Receipt;
import store.model.Stock;
import store.view.InputView;

public class PurchaseService {
    private final InputView inputView;
    private final ReceiptService receiptService;

    private List<PurchasedProduct> promotionProducts;
    private List<PurchasedProduct> purchasedProducts;

    public PurchaseService(InputView inputView, ReceiptService receiptService) {
        this.inputView = inputView;
        this.receiptService = receiptService;
    }

    public Receipt purchase(Map<String, Integer> purchase, Map<String, Promotion> promotions,
                            Map<String, Stock> stocks) {
        initLists();
        updatePromotionPurchase(purchase, promotions, stocks);
        boolean membership = inputView.getMembership();

        return receiptService.calculateReceipt(stocks, promotionProducts, purchasedProducts, membership);
    }

    private void initLists() {
        purchasedProducts = new ArrayList<>();
        promotionProducts = new ArrayList<>();
    }

    protected void updatePromotionPurchase(Map<String, Integer> purchase, Map<String, Promotion> promotions,
                                           Map<String, Stock> stocks) {
        for (String name : purchase.keySet()) {
            Stock stock = stocks.get(name);
            if (stock.isPromotion()) {
                checkPromotion(stocks.get(name), purchase.get(name), promotions.get(stock.getPromotionName()));
                continue;
            }
            purchasedProducts.add(new PurchasedProduct(name, purchase.get(name)));
        }
    }

    protected void checkPromotion(Stock stock, int purchaseQuantity, Promotion promotion) {
        int promotionUnit = promotion.getPromotionUnit();
        int neededPromotionUnit = purchaseQuantity / promotionUnit;
        int availablePromotionUnit = stock.getPromotionQuantity() / promotionUnit;
        int redundantQuantity = purchaseQuantity - promotionUnit * neededPromotionUnit;
        if(redundantQuantity == promotion.getRequiredQuantity()) {
            neededPromotionUnit++;
        }

        if (isPromotionQuantityShortage(availablePromotionUnit, neededPromotionUnit)) {
            checkNoPromotion(stock, purchaseQuantity, availablePromotionUnit, promotionUnit);
            return;
        }

        if (isAdditionalPromotionAvailable(redundantQuantity, promotion)) {
            checkAddPromotion(stock, purchaseQuantity, neededPromotionUnit);
            return;
        }

        addPurchase(stock.getName(), purchaseQuantity, neededPromotionUnit);
    }

    private boolean isPromotionQuantityShortage(int availablePromotionUnit, int neededPromotionUnit) {
        return availablePromotionUnit < neededPromotionUnit;
    }

    private boolean isAdditionalPromotionAvailable(int redundantQuantity, Promotion promotion) {
        return redundantQuantity >= promotion.getRequiredQuantity();
    }

    private void checkAddPromotion(Stock stock, int purchaseQuantity, int neededPromotionUnit) {
        boolean addPromotion = inputView.getPromotion(stock.getName());
        if (addPromotion) {
            addPurchase(stock.getName(), purchaseQuantity + 1, neededPromotionUnit);
            return;
        }

        addPurchase(stock.getName(), purchaseQuantity, neededPromotionUnit-1);
    }

    private void checkNoPromotion(Stock stock, int purchaseQuantity, int availablePromotionUnit, int promotionUnit) {
        int regularPriceQuantity = purchaseQuantity - availablePromotionUnit * promotionUnit;
        boolean notPromotion = inputView.getNotPromotion(stock.getName(), regularPriceQuantity);
        if (notPromotion) {
            addPurchase(stock.getName(), purchaseQuantity, availablePromotionUnit);
            return;
        }
        addPurchase(stock.getName(), purchaseQuantity - regularPriceQuantity, availablePromotionUnit);
    }

    private void addPurchase(String name, int purchaseQuantity, int promotionQuantity) {
        purchasedProducts.add(new PurchasedProduct(name, purchaseQuantity));
        promotionProducts.add(new PurchasedProduct(name, promotionQuantity));
    }

    public Map<String, Stock> updateStocks(Map<String, Stock> stocks, List<PurchasedProduct> receiptPurchasedProducts) {
        for (PurchasedProduct product : receiptPurchasedProducts) {
            String name = product.getName();
            stocks.get(name).updateStocks(product.getQuantity());
        }

        return stocks;
    }
}
