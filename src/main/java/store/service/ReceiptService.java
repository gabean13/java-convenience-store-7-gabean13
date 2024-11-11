package store.service;

import java.util.List;
import java.util.Map;
import store.model.PurchasedProduct;
import store.model.Receipt;
import store.model.Receipt.PurchaseInfo;
import store.model.Stock;

public class ReceiptService {
    static private final double MEMBERSHIP_DISCOUNT_RATE = 0.3;
    static private final int MAX_MEMBERSHIP_DISCOUNT = 8000;

    public Receipt calculateReceipt(Map<String, Stock> stocks, List<PurchasedProduct> promotionProducts, List<PurchasedProduct> purchasedProducts, boolean membership) {
        Receipt receipt = new Receipt();
        receipt.setPromotionProducts(updateProductsPrice(stocks, promotionProducts));
        receipt.setPurchasedProducts(updateProductsPrice(stocks, purchasedProducts));
        receipt.setPurchaseInfo(updatePurchaseInfo(stocks, promotionProducts, purchasedProducts, membership));

        return receipt;
    }

    protected List<PurchasedProduct> updateProductsPrice(Map<String, Stock> stocks, List<PurchasedProduct> purchasedProducts) {
        for (PurchasedProduct purchasedProduct : purchasedProducts) {
            int price = stocks.get(purchasedProduct.getName()).getPrice();
            int quantity = purchasedProduct.getQuantity();
            purchasedProduct.setTotalPrice(price * quantity);
        }

        return purchasedProducts;
    }

    protected PurchaseInfo updatePurchaseInfo(Map<String, Stock> stocks, List<PurchasedProduct> promotionProducts, List<PurchasedProduct> purchasedProducts, boolean membership) {
        int totalQuantity = purchasedProducts.stream().mapToInt(PurchasedProduct::getQuantity).sum();
        int totalPrice = purchasedProducts.stream().mapToInt(PurchasedProduct::getTotalPrice).sum();
        int promotionDiscountPrice = promotionProducts.stream().mapToInt(PurchasedProduct::getTotalPrice).sum();
        if (membership) {
            int membershipDiscountPrice = getMembershipDiscount(stocks, purchasedProducts, promotionProducts);
            return new PurchaseInfo(totalQuantity, totalPrice, promotionDiscountPrice, membershipDiscountPrice, totalPrice - promotionDiscountPrice - membershipDiscountPrice);
        }

        return new PurchaseInfo(totalQuantity, totalPrice, promotionDiscountPrice, 0, totalPrice - promotionDiscountPrice);
    }

    private int getMembershipDiscount(Map<String, Stock> stocks, List<PurchasedProduct> purchasedProducts, List<PurchasedProduct> promotionProducts) {
        int membershipAvailablePrice = 0;
        for (PurchasedProduct purchasedProduct : purchasedProducts) {
            Stock stock = stocks.get(purchasedProduct.getName());
            if (!stock.hasPromotion() || purchasedProduct.isNotIncludedInPromotion(promotionProducts)) {
                membershipAvailablePrice += purchasedProduct.getTotalPrice();
            }
        }

        return calculateMembershipDiscount(membershipAvailablePrice);
    }

    private int calculateMembershipDiscount(int membershipAvailablePrice) {
        membershipAvailablePrice *= MEMBERSHIP_DISCOUNT_RATE;
        if (membershipAvailablePrice > MAX_MEMBERSHIP_DISCOUNT) {
            membershipAvailablePrice = MAX_MEMBERSHIP_DISCOUNT;
        }

        return membershipAvailablePrice;
    }
}
