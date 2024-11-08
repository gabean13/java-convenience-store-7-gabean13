package store.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import net.bytebuddy.asm.Advice.Local;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.Promotion;
import store.model.Stock;

class InitialSettingServiceTest {
    InitialSettingService initialSettingService;
    FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        initialSettingService = new InitialSettingService(fileService);
    }

    @Test
    @DisplayName("promotion 파일을 불러와서 저장한다.")
    void success_convertFileToPromotions() {
        List<String> testPromotionContents = List.of(
                "탄산2+1,2,1,2024-01-01,2024-12-31",
                "MD추천상품,1,1,2024-01-01,2024-12-31"
        );

        Map<String, Promotion> promotions = initialSettingService.getPromotionMap(testPromotionContents);

        Assertions.assertThat(promotions).hasSize(2);
        Promotion promotion1 = promotions.get("탄산2+1");
        Assertions.assertThat(promotion1).hasFieldOrPropertyWithValue("name", "탄산2+1");
        Assertions.assertThat(promotion1).hasFieldOrPropertyWithValue("requiredQuantity", 2);
        Assertions.assertThat(promotion1).hasFieldOrPropertyWithValue("presentQuantity", 1);
        Assertions.assertThat(promotion1).hasFieldOrPropertyWithValue("startDate", LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        Assertions.assertThat(promotion1).hasFieldOrPropertyWithValue("endDate", LocalDateTime.of(2024, 12, 31, 23, 59, 59,999999999));

        Promotion promotion2 = promotions.get("MD추천상품");
        Assertions.assertThat(promotion2).hasFieldOrPropertyWithValue("name", "MD추천상품");
        Assertions.assertThat(promotion2).hasFieldOrPropertyWithValue("requiredQuantity", 1);
        Assertions.assertThat(promotion2).hasFieldOrPropertyWithValue("presentQuantity", 1);
        Assertions.assertThat(promotion2).hasFieldOrPropertyWithValue("startDate", LocalDateTime.of(2024, 1, 1, 0, 0, 0));
        Assertions.assertThat(promotion2).hasFieldOrPropertyWithValue("endDate", LocalDateTime.of(2024, 12, 31, 23, 59, 59,999999999));

    }

    @Test
    @DisplayName("product 파일을 불러와서 저장한다")
    void success_convertFileToStocks() {
        List<String> testProductContents = List.of(
                "콜라,1000,10,탄산2+1",
                "콜라,1000,10,null",
                "사이다,1000,8,탄산2+1"
        );

        List<Stock> stocks = initialSettingService.getStocks(testProductContents);

        Assertions.assertThat(stocks).hasSize(2);
        Stock stock1 = stocks.get(0);
        Assertions.assertThat(stock1).hasFieldOrPropertyWithValue("generalQuantity", 10);
        Assertions.assertThat(stock1).hasFieldOrPropertyWithValue("promotionQuantity", 10);
        Stock stock2 = stocks.get(1);
        Assertions.assertThat(stock2).hasFieldOrPropertyWithValue("generalQuantity", 0);
        Assertions.assertThat(stock2).hasFieldOrPropertyWithValue("promotionQuantity", 8);
    }
}