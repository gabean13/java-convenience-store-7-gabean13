package store;

import java.util.List;
import java.util.Map;
import store.model.Promotion;
import store.model.Stock;
import store.service.InitialSettingService;
import store.view.OutputView;

public class ConvenienceStore {
    private final InitialSettingService initialSettingService;
    private final OutputView outputView;

    private final Map<String, Promotion> promotions;
    private final List<Stock> stocks;

    public ConvenienceStore() {
        initialSettingService = new InitialSettingService();
        outputView = new OutputView();

        this.promotions = initialSettingService.convertFileToPromotions();
        this.stocks = initialSettingService.convertFileToStocks();
    }

    public void run() {
        //0. 구매 안내
        outputView.printGreetingAndMenu(stocks);
        //1. 구매

        //2. 종료
    }
}
