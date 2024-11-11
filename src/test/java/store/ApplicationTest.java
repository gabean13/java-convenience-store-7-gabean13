package store;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static camp.nextstep.edu.missionutils.test.Assertions.assertNowTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {
    @Test
    void 파일에_있는_상품_목록_출력() {
        assertSimpleTest(() -> {
            run("[물-1]", "N", "N");
            assertThat(output()).contains(
                "- 콜라 1,000원 10개 탄산2+1",
                "- 콜라 1,000원 10개",
                "- 사이다 1,000원 8개 탄산2+1",
                "- 사이다 1,000원 7개",
                "- 오렌지주스 1,800원 9개 MD추천상품",
                "- 오렌지주스 1,800원 재고 없음",
                "- 탄산수 1,200원 5개 탄산2+1",
                "- 탄산수 1,200원 재고 없음",
                "- 물 500원 10개",
                "- 비타민워터 1,500원 6개",
                "- 감자칩 1,500원 5개 반짝할인",
                "- 감자칩 1,500원 5개",
                "- 초코바 1,200원 5개 MD추천상품",
                "- 초코바 1,200원 5개",
                "- 에너지바 2,000원 5개",
                "- 정식도시락 6,400원 8개",
                "- 컵라면 1,700원 1개 MD추천상품",
                "- 컵라면 1,700원 10개"
            );
        });
    }

    @Test
    void 여러_개의_일반_상품_구매() {
        assertSimpleTest(() -> {
            run("[비타민워터-3],[물-2],[정식도시락-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈18,300");
        });
    }

    @Test
    void 기간에_해당하지_않는_프로모션_적용() {
        assertNowTest(() -> {
            run("[감자칩-2]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈3,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("[컵라면-12]", "N", "N");
            assertThat(output()).contains("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        });
    }

    @Test
    @DisplayName("2 + 1 프로모션을 포함해서 3개 구매하고 멤버십 할인을 받는다")
    void withPromotionAndMemberShip() {
        assertNowTest(() -> {
            run("[콜라-3]", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈2,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    @DisplayName("2 + 1 프로모션을 포함해서 3개 구매하고 멤버십 할인을 받지않는다")
    void withPromotionAndNoMemberShip() {
        assertNowTest(() -> {
            run("[콜라-3]", "Y", "N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈2,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    @DisplayName("2 + 1프로모션에서 2개 정가 구매를 하고 멤버십 할인을 받는다")
    void withPromotionWithTwoAndMemberShip() {
        assertNowTest(() -> {
            run("[콜라-2]", "N", "N", "N");
            assertThat(output().replaceAll("\\s", ""))
                    .contains("내실돈2,000")
                    .contains("현재콜라은(는)1개를무료로더받을수있습니다.")
                    .doesNotContain("-1,000");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    @DisplayName("2 + 1프로모션에서 2개 정가 구매를 하고 멤버십 할인을 받지 않는다")
    void withPromotionWithTwoAndNoMemberShip() {
        assertNowTest(() -> {
            run("[콜라-2]", "N", "Y", "N");
            assertThat(output().replaceAll("\\s", ""))
                    .contains("멤버십할인-600")
                    .contains("내실돈1,400")
                    .contains("현재콜라은(는)1개를무료로더받을수있습니다.");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    @DisplayName("2 + 1프로모션에서 2개 구매를 하여 1개 증정을 받고 멤버십 할인을 받는다")
    void withAddPromotionAndMemberShip() {
        assertNowTest(() -> {
            run("[콜라-2]", "Y", "Y", "N");
            assertThat(output().replaceAll("\\s", ""))
                    .contains("콜라3")
                    .contains("콜라1")
                    .contains("멤버십할인0")
                    .contains("내실돈2,000")
                    .contains("현재콜라은(는)1개를무료로더받을수있습니다.");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    @DisplayName("2 + 1프로모션에서 2개 구매를 하여 1개 증정을 받고 멤버십 할인을 받지 않는다")
    void withAddPromotionAndNoMemberShip() {
        assertNowTest(() -> {
            run("[콜라-2]", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", ""))
                    .contains("콜라3")
                    .contains("콜라1")
                    .contains("멤버십할인0")
                    .contains("내실돈2,000")
                    .contains("현재콜라은(는)1개를무료로더받을수있습니다.");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    @DisplayName("2 + 1프로모션에서 1개 구매를 하고 멤버십 할인을 받는다")
    void WithNoPromotionAndMemberShip() {
        assertNowTest(() -> {
            run("[콜라-1]", "Y", "N");
            assertThat(output().replaceAll("\\s", ""))
                    .contains("콜라1")
                    .contains("멤버십할인-300")
                    .contains("내실돈700")
                    .doesNotContain("현재콜라은(는)1개를무료로더받을수있습니다.");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    @DisplayName("2 + 1프로모션에서 1개 구매를 하고 멤버십 할인을 받지않는다")
    void withNoPromotionAndNoMemberShip() {
        assertNowTest(() -> {
            run("[콜라-1]", "N", "N");
            assertThat(output().replaceAll("\\s", ""))
                    .contains("콜라1")
                    .contains("멤버십할인0")
                    .contains("내실돈1,000")
                    .doesNotContain("현재콜라은(는)1개를무료로더받을수있습니다.");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    @DisplayName("갱신된 재고로 재 구매한다")
    void updateStockTest() {
        assertNowTest(() -> {
            run("[콜라-20],[에너지바-5]", "Y", "N", "Y", "[콜라-5]", "[탄산수-3]", "N", "N");
            assertThat(output())
                    .contains("- 콜라 1,000원 재고 없음 탄산2+1")
                    .contains("- 콜라 1,000원 재고 없음")
                    .contains("- 에너지바 2,000원 재고 없음");
        }, LocalDate.of(2024, 2, 1).atStartOfDay());
    }

    @Test
    @DisplayName("예제 테스트")
    void exampleTest() {
        assertNowTest(() -> {
            run("[콜라-3],[에너지바-5]", "Y", "Y", "[콜라-10]", "Y", "N", "Y", "[오렌지주스-1]", "Y", "Y", "N");
            assertThat(output().replaceAll("\\s", ""))
                    .contains("내실돈9,000")
                    .contains("-콜라1,000원7개탄산2+1")
                    .contains("현재콜라4개는프로모션할인이적용되지않습니다.")
                    .contains("내실돈8,000")
                    .contains("현재오렌지주스은(는)1개를무료로더받을수있습니다.")
                    .contains("내실돈1,800");
        }, LocalDate.of(2024, 11, 11).atStartOfDay());
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
