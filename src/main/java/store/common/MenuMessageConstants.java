package store.common;

public class MenuMessageConstants {
    //기본 안내 메세지
    public static final String GREETING = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";
    public static final String PURCHASE_GUIDE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])\n";
    public static final String PROMOTION_BENEFIT_GUIDE = "현재 {0}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    public static final String PROMOTION_OUT_OF_STOCK_GUIDE = "현재 {0} {1}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
    public static final String MEMBERSHIP_GUIDE = "멤버십 할인을 받으시겠습니까? (Y/N)\n";
    public static final String ADDITIONAL_PURCHASE_GUIDE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)\n";

    //메뉴 출력
    public static final String MENU_GUIDE_WITH_STOCK_PROMOTION = "- {0} {1}원 {2}개 {3}\n";
    public static final String MENU_GUIDE_WITH_STOCK = "- {0} {1}원 {2}개\n";
    public static final String MENU_GUIDE_OUT_OF_STOCK_PROMOTION = "- {0} {1}원 재고 없음 {2}\n";
    public static final String MENU_GUIDE_OUT_OF_STOCK = "- {0} {1}원 재고없음\n";
}