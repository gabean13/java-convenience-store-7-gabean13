package store.model;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchasedProductTest {
    PurchasedProduct product;

    @BeforeEach
    void setUp() {
        product = new PurchasedProduct("test", 1000);
        product.setTotalPrice(20000);
    }

    @Test
    @DisplayName("프로모션 구매 리스트에 없는지 검사한다")
    void isNotIncludedInPromotionTest() {
        List<PurchasedProduct> promotionProducts = List.of(new PurchasedProduct("test", 1000));
        List<PurchasedProduct> promotionProductNotIncluded = List.of(new PurchasedProduct("test2", 1000), new PurchasedProduct("test", 0));

        boolean includedInPromotion = product.isNotIncludedInPromotion(promotionProducts);
        boolean notIncludedInPromotion = product.isNotIncludedInPromotion(promotionProductNotIncluded);

        Assertions.assertThat(includedInPromotion).isFalse();
        Assertions.assertThat(notIncludedInPromotion).isTrue();
    }

    @Test
    @DisplayName("구매 정보를 정렬하여 반환한다")
    void getLineUpProductInfoTest() {
        String productInfo = product.getLineUpProductInfo();

        Assertions.assertThat(productInfo.length()).isEqualTo(36);
        Assertions.assertThat(productInfo).contains("test").contains("1,000").contains("20,000");
    }

    @Test
    @DisplayName("증정 정보를 정렬하여 반환한다")
    void getLinUpPromotionInfoTest() {
        String promotionInfo = product.getLinUpPromotionInfo();

        Assertions.assertThat(promotionInfo.length()).isEqualTo(36);
        Assertions.assertThat(promotionInfo).contains("test").contains("1,000").doesNotContain("20,000");
    }
}