package store.model;

public class Stock {
    private Product product;
    private int quantity;

    public Stock(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
