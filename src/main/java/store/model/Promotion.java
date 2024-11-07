package store.model;

import java.util.Date;

public class Promotion {
    private String name;
    private int requiredQuantity;
    private int presentQuantity;
    private Date startDate;
    private Date endDate;

    public Promotion(String name, int requiredQuantity, int presentQuantity, Date startDate, Date endDate) {
        this.name = name;
        this.requiredQuantity = requiredQuantity;
        this.presentQuantity = presentQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
