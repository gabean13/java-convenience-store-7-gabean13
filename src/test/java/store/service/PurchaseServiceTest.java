package store.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.Product;
import store.model.Promotion;
import store.model.PurchasedProduct;
import store.model.Receipt;
import store.model.Stock;
import store.view.InputValidator;
import store.view.InputView;

class PurchaseServiceTest {
    PurchaseService purchaseService;
    Map<String, Promotion> promotions;
    Map<String, Stock> stocks;

    List<PurchasedProduct> promotionProducts;
    List<PurchasedProduct> purchasedProducts;

    @BeforeEach
    void setUp() {
        purchaseService = new PurchaseService(new InputView(new InputValidator()), new ReceiptService());
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59, 59, 999999999);
        promotions = Map.of(
                "testPromo1", new Promotion("testPromo1", 1, 1, startDate, endDate),
                "testPromo2", new Promotion("testPromo2", 2, 1, startDate, endDate),
                "testPromo3", new Promotion("testPromo3", 3, 1, startDate, endDate)
        );

        Stock testStock1 = new Stock(new Product("testProduct1", 1, "testPromo1"));
        testStock1.setGeneralQuantity(10);
        testStock1.setPromotionQuantity(10);
        Stock testStock2 = new Stock(new Product("testProduct2", 2, "testPromo2"));
        testStock2.setGeneralQuantity(0);
        testStock2.setPromotionQuantity(10);
        Stock testStock3 = new Stock(new Product("testProduct3", 3, "testPromo3"));
        testStock3.setGeneralQuantity(10);
        testStock3.setPromotionQuantity(0);
        stocks = Map.of(
                "testProduct1", testStock1,
                "testProduct2", testStock2,
                "testProduct3", testStock3
        );


    }

    @Test
    @DisplayName("영수증에 따라 재고를 갱신한다")
    void updateStocksTest() {
        List<PurchasedProduct> purchasedProducts = List.of(
                new PurchasedProduct("testProduct1", 12),
                new PurchasedProduct("testProduct2", 5),
                new PurchasedProduct("testProduct3", 10)
        );

        Map<String, Stock> updatedStocks = purchaseService.updateStocks(stocks, purchasedProducts);

        Stock testStock1 = updatedStocks.get("testProduct1");
        Assertions.assertThat(testStock1.getTotalQuantity()).isEqualTo(8);
        Assertions.assertThat(testStock1.getPromotionQuantity()).isEqualTo(0);
        Stock testStock2 = updatedStocks.get("testProduct2");
        Assertions.assertThat(testStock2.getTotalQuantity()).isEqualTo(5);
        Assertions.assertThat(testStock2.getPromotionQuantity()).isEqualTo(5);
        Stock testStock3 = updatedStocks.get("testProduct3");
        Assertions.assertThat(testStock3.getTotalQuantity()).isEqualTo(0);
        Assertions.assertThat(testStock3.getPromotionQuantity()).isEqualTo(0);

    }
}