package store.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {
    Stock stockWithPromo;
    Stock stockWithoutPromo;

    @BeforeEach
    void setUp() {
        stockWithPromo = new Stock(new Product("콜라", 1000, "탄산2+1"));
        stockWithPromo.setGeneralQuantity(10);
        stockWithPromo.setPromotionQuantity(5);

        stockWithoutPromo = new Stock(new Product("감자칩", 1500, "null"));
        stockWithoutPromo.setGeneralQuantity(10);
        stockWithoutPromo.setPromotionQuantity(0);
    }

    @Test
    @DisplayName("메뉴 출력 관련 정보를 반환한다")
    void getMenuDetailsTest() {
        String menuDetailsWithPromo = stockWithPromo.getMenuDetails();
        String menuDetailsWithoutPromo = stockWithoutPromo.getMenuDetails();

        Assertions.assertThat(menuDetailsWithPromo).contains("- 콜라 1,000원 5개 탄산2+1").contains("- 콜라 1,000원 10개");
        Assertions.assertThat(menuDetailsWithoutPromo).contains("- 감자칩 1,500원 10개");
    }

    @Test
    @DisplayName("프로모션 여부를 검사한다")
    void isPromotionTest() {
        boolean withPromotion = stockWithPromo.isPromotion();
        boolean withoutPromotion = stockWithoutPromo.isPromotion();

        Assertions.assertThat(withPromotion).isTrue();
        Assertions.assertThat(withoutPromotion).isFalse();
    }

    @Test
    @DisplayName("Product name이 동일하면 동일한 객체로 판단한다")
    void equalsTest() {
        Stock sameStock = new Stock(new Product("콜라", 10000, "null"));

        boolean equals = sameStock.equals(stockWithPromo);

        Assertions.assertThat(equals).isTrue();
    }

    @Test
    @DisplayName("구매수량에 따라 프로모션,일반 순으로 재고를 갱신한다")
    void updateStocksTest() {
        stockWithPromo.updateStocks(7);

        int promotionQuantity = stockWithPromo.getPromotionQuantity();
        int totalQuantity = stockWithPromo.getTotalQuantity();

        Assertions.assertThat(promotionQuantity).isEqualTo(0);
        Assertions.assertThat(totalQuantity).isEqualTo(8);
    }

}