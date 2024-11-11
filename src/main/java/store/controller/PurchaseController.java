package store.controller;

import java.util.Map;
import store.common.ErrorMessageConstants;
import store.model.Promotion;
import store.model.Receipt;
import store.model.Stock;
import store.service.PurchaseService;
import store.view.InputView;
import store.view.OutputView;

public class PurchaseController {
    private final OutputView outputView;
    private final InputView inputView;
    private final PurchaseService purchaseService;

    public PurchaseController(OutputView outputView, InputView inputView, PurchaseService purchaseService) {
        this.outputView = outputView;
        this.inputView = inputView;
        this.purchaseService = purchaseService;
    }

    public void purchase(Map<String, Promotion> promotions, Map<String, Stock> stocks) {
        while (true) {
            outputView.printGreetingAndMenu(stocks);
            Receipt receipt = purchaseService.purchase(getPurchaseInput(stocks), promotions, stocks);
            stocks = purchaseService.updateStocks(stocks, receipt.getPurchasedProducts());
            outputView.printReceipt(receipt);
            if (!inputView.getAdditionalPurchase()) {
                return;
            }
        }
    }

    protected Map<String, Integer> getPurchaseInput(Map<String, Stock> stocks) {
        while (true) {
            Map<String, Integer> purchaseProducts = inputView.getPurchaseItems();
            if (isProductNotFound(purchaseProducts, stocks)) {
                outputView.printError(ErrorMessageConstants.ERROR_PRODUCT_NOT_FOUND);
                continue;
            } else if (isStockShortage(purchaseProducts, stocks)) {
                outputView.printError(ErrorMessageConstants.ERROR_EXCEEDED_STOCK_QUANTITY);
                continue;
            }
            return purchaseProducts;
        }
    }

    private boolean isStockShortage(Map<String, Integer> purchaseProducts, Map<String, Stock> stocks) {
        for (String name : purchaseProducts.keySet()) {
            Integer purchaseQuantity = purchaseProducts.get(name);
            if (stocks.get(name).getTotalQuantity() < purchaseQuantity) {
                return true;
            }
        }
        return false;
    }

    private boolean isProductNotFound(Map<String, Integer> purchaseProducts, Map<String, Stock> stocks) {
        for (String name : purchaseProducts.keySet()) {
            if (!stocks.containsKey(name)) {
                return true;
            }
        }
        return false;
    }
}
