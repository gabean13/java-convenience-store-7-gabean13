package store.common;

public class ErrorMessageConstants {
    public static final String ERROR_MESSAGE_PREFIX = "\n[ERROR] ";
    public static final String ERROR_INVALID_PURCHASE_FORMAT = ERROR_MESSAGE_PREFIX + "올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    public static final String ERROR_PRODUCT_NOT_FOUND = ERROR_MESSAGE_PREFIX + "존재하지 않는 상품입니다. 다시 입력해 주세요.";
    public static final String ERROR_EXCEEDED_STOCK_QUANTITY = ERROR_MESSAGE_PREFIX + "재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    public static final String ERROR_INVALID_INPUT = ERROR_MESSAGE_PREFIX + "잘못된 입력입니다. 다시 입력해 주세요.";
}
