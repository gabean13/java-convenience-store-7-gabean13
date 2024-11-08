package store.model;

import java.text.MessageFormat;
import store.common.CommonMessageConstants;
import store.common.MenuMessageConstants;

public class Product {
    private String name;
    private int price;
    private String promotionName;

    public Product(String name, int price, String promotionName) {
        this.name = name;
        this.price = price;
        this.promotionName = promotionName;
    }

    public String getGeneralMenuDetail(int quantity) {
        if (quantity == 0) {
            return MessageFormat.format(MenuMessageConstants.MENU_GUIDE_OUT_OF_STOCK, name, price);
        }
        return MessageFormat.format(MenuMessageConstants.MENU_GUIDE_WITH_STOCK, name, price, quantity);
    }

    public String getPromotionMenuDetail(int quantity) {
        if (isPromotionProduct()) {
            if (quantity == 0) {
                return MessageFormat.format(MenuMessageConstants.MENU_GUIDE_OUT_OF_STOCK_PROMOTION, name, price,
                        promotionName);
            }
            return MessageFormat.format(MenuMessageConstants.MENU_GUIDE_WITH_STOCK_PROMOTION, name, price, quantity,
                    promotionName);
        }
        return CommonMessageConstants.EMPTY_STRING;
    }

    public boolean isPromotionProduct() {
        return !promotionName.equals(CommonMessageConstants.NULL_STRING);
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Product) {
            Product product = (Product) o;
            return this.name.equals(product.name);
        }

        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
