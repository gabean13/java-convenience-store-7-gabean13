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

    public void savePromotionQuantity(int promotionQuantity) {
        this.promotionQuantity = promotionQuantity;
    }

    public void saveGeneralQuantity(int generalQuantity) {
        this.generalQuantity = generalQuantity;
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

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Stock) {
            Stock stock = (Stock) o;
            return this.product.equals(stock.product);
        }
        return super.equals(o);
    }
}
