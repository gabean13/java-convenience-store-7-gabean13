package store.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    @DisplayName("일반 상품에 대한 정보를 반환한다")
    void getGeneralMenuDetailTest() {
        int quantity = 10;
        int zeroQuantity = 0;
        Product product = new Product("test", 1000, "null");

        String generalMenuDetail = product.getGeneralMenuDetail(quantity);
        String zeroGeneralMenuDetail = product.getGeneralMenuDetail(zeroQuantity);

        Assertions.assertThat(generalMenuDetail).contains("- test 1,000원 10개");
        Assertions.assertThat(zeroGeneralMenuDetail).contains("- test 1,000원 재고 없음");
    }


    @Test
    @DisplayName("프로모션 상품에 대한 정보를 반환한다")
    void getPromotionMenuDetailTest() {
        int quantity = 10;
        int zeroQuantity = 0;
        Product product = new Product("test", 1000, "promo");

        String promotionMenuDetail = product.getPromotionMenuDetail(quantity);
        String zeroPromotionMenuDetail = product.getPromotionMenuDetail(zeroQuantity);

        Assertions.assertThat(promotionMenuDetail).contains("- test 1,000원 10개 promo");
        Assertions.assertThat(zeroPromotionMenuDetail).contains("- test 1,000원 재고 없음 promo");
    }

    @Test
    @DisplayName("프로모션 상품 여부에 대해 반환한다")
    void isPromotionProductTest() {
        Product promotionProduct = new Product("test", 1000, "promo");
        Product noPromotionProduct = new Product("test", 1000, "null");

        Assertions.assertThat(promotionProduct.isPromotionProduct()).isTrue();
        Assertions.assertThat(noPromotionProduct.isPromotionProduct()).isFalse();
    }

    @Test
    @DisplayName("name이 동일하면 동일한 객체로 판단한다")
    void equalsTest() {
        Product sameProduct1= new Product("name", 1000, "promo");
        Product sameProduct2 = new Product("name", 1200, "null");
        Product differentProduct = new Product("hello", 1200, "null");

        Assertions.assertThat(sameProduct1).isEqualTo(sameProduct2);
        Assertions.assertThat(sameProduct1).isNotEqualTo(differentProduct);
    }
}