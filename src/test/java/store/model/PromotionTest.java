package store.model;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionTest {
    Promotion promotion;

    @BeforeEach
    void setUp() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59, 59, 999999999);

        promotion = new Promotion("test", 2, 1, startDate, endDate);
    }

    @Test
    @DisplayName("프로모션 단위를 반환한다")
    void getPromotionUnitTest() {
        int promotionUnit = promotion.getPromotionUnit();

        Assertions.assertThat(promotionUnit).isEqualTo(3);
    }

    @Test
    @DisplayName("구매한 양과 프로모션 요구 양이 동일한지 검사한다")
    void getIsPromotionShortage() {
        boolean promotionShortage = promotion.isPromotionShortage(2);

        Assertions.assertThat(promotionShortage).isTrue();
    }
}