package store.model;

import java.util.List;

public class Receipt {
    private List<PurchasedProduct> purchasedProducts;
    private List<PurchasedProduct> promotionProducts;
    private PurchaseInfo purchaseInfo;

    private class PurchasedProduct {
        private Product product;
        private int quantity;
        private int totalPrice;

        public PurchasedProduct(Product product, int quantity, int totalPrice) {
            this.product = product;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
        }
    }

    private class PurchaseInfo {
        private int totalQuantity;
        private int totalPrice;
        private int promotionDiscountPrice;
        private int membershipDiscountPrice;
        private int finalPrice;

        public PurchaseInfo(int totalQuantity, int totalPrice, int promotionDiscountPrice, int membershipDiscountPrice,
                            int finalPrice) {
            this.totalQuantity = totalQuantity;
            this.totalPrice = totalPrice;
            this.promotionDiscountPrice = promotionDiscountPrice;
            this.membershipDiscountPrice = membershipDiscountPrice;
            this.finalPrice = finalPrice;
        }
    }
}
