package store.view;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class InputValidatorTest {
    InputValidator inputValidator;

    @BeforeEach
    void setUp() {
        inputValidator = new InputValidator();
    }

    @ParameterizedTest
    @DisplayName("구매할 상품명 수량 형식대로 입력한다")
    @ValueSource(strings = {"[콜라-1],[감자칩-2]", "[사이다-10]"})
    void success_isPurchaseFormatValidTest(String input) {
        inputValidator.isPurchaseFormatValid(input);
        //아무일도 일어나지 않음
    }

    @ParameterizedTest
    @DisplayName("구매할 상품명 수량 형식이 틀리면 예외가 발생한다")
    @ValueSource(strings = {"[안녕]", "[감자칩-2][콜라-2]", "[감자칩-abcd]","콜라-1,사이다-2", "[1-콜라],[2-사이다], [콜라-0]"})
    void exception_isPurchaseFormatValidTest(String input) {
        assertThatThrownBy(() -> inputValidator.isPurchaseFormatValid(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("Y / N 형식대로 입력한다")
    @ValueSource(strings = {"Y", "N"})
    void success_isYesOrNoFormatValidTest(String input) {
        inputValidator.isYesOrNoFormatValid(input);
        //아무일도 일어나지 않음
    }

    @ParameterizedTest
    @DisplayName("Y / N 형식이 틀리면 예외가 발생한다")
    @ValueSource(strings = {"abc", "123", "히히"})
    void exception_isYesOrNoFormatValidTest(String input) {
        assertThatThrownBy(() -> inputValidator.isYesOrNoFormatValid(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}