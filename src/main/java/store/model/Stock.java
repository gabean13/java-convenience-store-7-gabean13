package store.model;

public class Stock {
    private Product product;
    private int generalQuantity;
    private int promotionQuantity;

    public Stock(Product product) {
        this.product = product;
        this.generalQuantity = 0;
        this.promotionQuantity = 0;
    }

    public String getMenuDetails() {
        StringBuilder sb = new StringBuilder();
        String promotionDetails = product.getPromotionMenuDetail(promotionQuantity);
        if (!promotionDetails.isBlank()) {
            sb.append(promotionDetails);
        }
        sb.append(product.getGeneralMenuDetail(generalQuantity));

        return sb.toString();
    }

    public boolean isPromotion() {
        return product.isPromotionProduct();
    }

    public void updateStocks(int quantity) {
        if (product.isPromotionProduct() && promotionQuantity > 0) {
            promotionQuantity -= quantity;
            if (promotionQuantity < 0) {
                generalQuantity += promotionQuantity;
                promotionQuantity = 0;
            }
            return;
        }

        generalQuantity -= quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Stock stock) {
            return this.product.equals(stock.product);
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return product.hashCode();
    }

    public String getName() {
        return product.getName();
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public int getTotalQuantity() {
        return generalQuantity + promotionQuantity;
    }

    public String getPromotionName() {
        return product.getPromotionName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public void setPromotionQuantity(int promotionQuantity) {
        this.promotionQuantity = promotionQuantity;
    }

    public void setGeneralQuantity(int generalQuantity) {
        this.generalQuantity = generalQuantity;
    }
}
