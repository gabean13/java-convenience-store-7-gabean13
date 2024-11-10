package store;

import java.util.Map;
import store.controller.PurchaseController;
import store.model.Promotion;
import store.model.Stock;
import store.service.InitialSettingService;

public class ConvenienceStore {
    private final PurchaseController purchaseController;
    private final InitialSettingService initialSettingService;

    public ConvenienceStore(PurchaseController purchaseController, InitialSettingService initialSettingService) {
        this.purchaseController = purchaseController;
        this.initialSettingService = initialSettingService;
    }

    public void run() {
        Map<String, Promotion> promotions = initialSettingService.convertFileToPromotions();
        Map<String, Stock> stocks = initialSettingService.convertFileToStocks(promotions);
        purchaseController.purchase(promotions, stocks);
    }
}
