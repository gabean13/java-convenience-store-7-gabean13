package store.model;

import java.time.LocalDateTime;

public class Promotion {
    private String name;
    private int requiredQuantity;
    private int presentQuantity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Promotion(String name, int requiredQuantity, int presentQuantity, LocalDateTime startDate,
                     LocalDateTime endDate) {
        this.name = name;
        this.requiredQuantity = requiredQuantity;
        this.presentQuantity = presentQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int promotionUnit() {
        return requiredQuantity + presentQuantity;
    }

    public boolean promotionShortage(int purchaseQuantity) {
        return purchaseQuantity == requiredQuantity;
    }
}
