package store.model;

import java.util.List;
import store.common.MenuMessageConstants;

public class Receipt {
    private List<PurchasedProduct> purchasedProducts;
    private List<PurchasedProduct> promotionProducts;
    private PurchaseInfo purchaseInfo;

    private static String padString(String str, int length) {
        int padSize = length - str.length();
        if (padSize <= 0) {
            return str;
        }
        return str + " ".repeat(padSize);
    }

    private static String padNumber(int number, int length) {
        String stringNumber = String.format("%,d", number);
        int padSize = length - stringNumber.length();
        if (padSize <= 0) {
            return stringNumber;
        }
        return stringNumber + " ".repeat(padSize);
    }

    public void setPurchaseInfo(PurchaseInfo purchaseInfo) {
        this.purchaseInfo = purchaseInfo;
    }

    public String lineUpPurchasedProductsReceipt() {
        StringBuilder sb = new StringBuilder();
        for (PurchasedProduct product : purchasedProducts) {
            if (product.getQuantity() == 0) {
                continue;
            }
            sb.append(product.getLineUpProductInfo());
        }

        return sb.toString();
    }

    public String lineUpPromotionProductsReceipt() {
        StringBuilder sb = new StringBuilder();
        for (PurchasedProduct product : promotionProducts) {
            if (product.getQuantity() == 0) {
                continue;
            }
            sb.append(product.getLinUpPromotionInfo());
        }
        return sb.toString();
    }

    public String lineUpPurchaseInfo() {
        return purchaseInfo.getLineupPurchaseInfo();
    }

    public List<PurchasedProduct> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(List<PurchasedProduct> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public List<PurchasedProduct> getPromotionProducts() {
        return promotionProducts;
    }

    public void setPromotionProducts(List<PurchasedProduct> promotionProducts) {
        this.promotionProducts = promotionProducts;
    }

    public static class PurchasedProduct {
        private String name;
        private int quantity;
        private int totalPrice;

        public PurchasedProduct(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
            this.totalPrice = 0;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public boolean isNotIncludedInPromotion(List<PurchasedProduct> promotionProducts) {
            for (PurchasedProduct promotionProduct : promotionProducts) {
                if (promotionProduct.getName().equals(name) && promotionProduct.getQuantity() == 0) {
                    return true;
                }
            }
            return false;
        }

        public String getLineUpProductInfo() {
            return String.format("%s%s%s\n", padString(name, 19), padNumber(quantity, 10), padNumber(totalPrice, 6));
        }

        public String getLinUpPromotionInfo() {
            return String.format("%s%s%s\n", padString(name, 19), padNumber(quantity, 10), padString("", 6));
        }
    }

    public static class PurchaseInfo {
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

        public String getLineupPurchaseInfo() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%s%s%s\n", padString(MenuMessageConstants.TOTAL_PRICE, 19), padNumber(totalQuantity, 10), padNumber(totalPrice, 6)));
            sb.append(String.format("%s%s%s\n", padString(MenuMessageConstants.PROMOTION_DISCOUNT_PRICE, 19), padString(" ", 10), padNumber(-promotionDiscountPrice, 6)));
            sb.append(String.format("%s%s%s\n", padString(MenuMessageConstants.MEMBERSHIP_DISCOUNT_PRICE, 19), padString(" ", 10), padNumber(-membershipDiscountPrice, 6)));
            sb.append(String.format("%s%s%s", padString(MenuMessageConstants.FINAL_PRICE, 19), padString(" ", 10), padNumber(finalPrice, 6)));

            return sb.toString();
        }
    }
}
