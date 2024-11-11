package store.model;

import java.util.List;

public class PurchasedProduct {
    private String name;
    private int quantity;
    private int totalPrice;

    public PurchasedProduct(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = 0;
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

    private String padString(String str, int length) {
        int padSize = length - str.length();
        if (padSize <= 0) {
            return str;
        }
        return str + " ".repeat(padSize);
    }

    private String padNumber(int number, int length) {
        String stringNumber = String.format("%,d", number);
        int padSize = length - stringNumber.length();
        if (padSize <= 0) {
            return stringNumber;
        }
        return stringNumber + " ".repeat(padSize);
    }
}
