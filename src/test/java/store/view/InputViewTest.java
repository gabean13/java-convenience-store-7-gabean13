package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputViewTest {
    InputView inputView;
    InputStream input;
    PrintStream output;
    ByteArrayOutputStream stream;

    @BeforeEach
    void setUp() {
        inputView = new InputView(new InputValidator());
        input = System.in;
        output = System.out;
        stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream, true));
    }

    @Test
    @DisplayName("구매 형식을 입력 받는다")
    void success_getPurchaseItems() {
        String input = "[콜라-10],[사이다-3]\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Console.close();

        Map<String, Integer> items = inputView.getPurchaseItems();

        Assertions.assertThat(items)
                .containsEntry("콜라", 10)
                .containsEntry("사이다", 3);
    }

    @Test
    @DisplayName("구매 형식을 잘못 입력하면 안내 메세지와 함께 다시 입력 받는다")
    void exception_getPurchaseItems() {
        String input = "콜라,사이다-3\n[콜라-10],[사이다-3]\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Console.close();

        Map<String, Integer> items = inputView.getPurchaseItems();

        Assertions.assertThat(items)
                .containsEntry("콜라", 10)
                .containsEntry("사이다", 3);
    }

    @Test
    @DisplayName("구매 입력을 Map으로 매핑한다")
    void convertInputToItemsTest() {
        String input = "[감자-1],[감자-2],[초콜렛-10]";
        Map<String, Integer> items = inputView.convertInputToItems(input);

        Assertions.assertThat(items)
                .containsEntry("감자" , 3)
                .containsEntry("초콜렛", 10);
    }

    @Test
    @DisplayName("Y 입력 시 true 매핑한다")
    void success_getYMembershipTest() {
        String yInput = "Y\n";
        System.setIn(new ByteArrayInputStream(yInput.getBytes()));
        Console.close();

        boolean yMembership = inputView.getMembership();

        Assertions.assertThat(yMembership).isTrue();
    }

    @Test
    @DisplayName("N 입력 시 false로 매핑한다")
    void success_getXMembershipTest() {
        String xInput = "N\n";
        System.setIn(new ByteArrayInputStream(xInput.getBytes()));
        Console.close();

        boolean xMembership = inputView.getMembership();

        Assertions.assertThat(xMembership).isFalse();
    }



    @Test
    @DisplayName("Y / N가 아닌 값 입력 시 다시 입력 받는다")
    void exception_getMembershipTest() {
        String xInput = "123asdjfh\nN\n";
        System.setIn(new ByteArrayInputStream(xInput.getBytes()));
        Console.close();

        boolean membership = inputView.getMembership();

        Assertions.assertThat(membership).isFalse();
    }

    @AfterEach
    void tearDown() {
        System.setIn(input);
        System.setOut(output);
        Console.close();
    }
}