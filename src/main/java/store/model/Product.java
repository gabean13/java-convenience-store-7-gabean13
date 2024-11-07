package store.model;

public class Product {
    private String name;
    private int price;
    private Promotion promotion;

    public Product(String name, int price, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
    }
}
