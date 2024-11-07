package store.common;

public class MessageConstants {
    //기본 안내 메세지
    public static final String GREETING = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    public static final String PURCHASE_GUIDE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])\n";
    public static final String PROMOTION_BENEFIT_GUIDE = "현재 {0}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    public static final String PROMOTION_OUT_OF_STOCK_GUIDE = "현재 {0} {1}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
    public static final String MEMBERSHIP_GUIDE = "멤버십 할인을 받으시겠습니까? (Y/N)\n";
    public static final String ADDITIONAL_PURCHASE_GUIDE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)\n";

    //에러 메세지
    public static final String ERROR_MESSAGE_PREFIX ="[ERROR] ";
    public static final String ERROR_INVALID_PURCHASE_FORMAT = "올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.\n";
    public static final String ERROR_PRODUCT_NOT_FOUND = "존재하지 않는 상품입니다. 다시 입력해 주세요.\n";
    public static final String ERROR_EXCEEDED_STOCK_QUANTITY= "재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.\n";
    public static final String ERROR_INVALID_INPUT = "잘못된 입력입니다. 다시 입력해 주세요.\n";
}