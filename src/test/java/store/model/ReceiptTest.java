package store.model;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.Receipt.PurchaseInfo;

class ReceiptTest {
    Receipt receipt;
    List<PurchasedProduct> purchasedProducts;
    List<PurchasedProduct> promotionProducts;
    PurchaseInfo purchaseInfo;

    @BeforeEach
    void setUp() {
        PurchasedProduct product1 = new PurchasedProduct("purchase1", 2);
        product1.setTotalPrice(10000);
        PurchasedProduct product2 = new PurchasedProduct("purchase2", 3);
        product2.setTotalPrice(1000);
        PurchasedProduct product3 = new PurchasedProduct("purchase3", 0);
        PurchasedProduct promotion1 = new PurchasedProduct("promotion1", 1);
        PurchasedProduct promotion2 = new PurchasedProduct("promotion2", 2);
        PurchasedProduct promotion3 = new PurchasedProduct("promotion3", 0);
        purchasedProducts = List.of(product1, product2, product3);
        promotionProducts = List.of(promotion1, promotion2, promotion3);
        purchaseInfo = new PurchaseInfo(9, 10000, 2000, 1000, 7000);
        receipt = new Receipt(purchasedProducts, promotionProducts, purchaseInfo);
    }

    @Test
    @DisplayName("구매한 목록을 정렬한다")
    void lineUpPurchasedProductsReceiptTest() {
        String productsReceipt = receipt.lineUpPurchasedProductsReceipt();

        Assertions.assertThat(productsReceipt)
                .doesNotContain("purchase3")
                .contains("purchase1").contains("10,000")
                .contains("purchase2").contains("1,000");
    }

    @Test
    @DisplayName("프로모션 목록을 정렬한다")
    void lineUpPromotionProductsReceiptTest() {
        String promotionReceipt = receipt.lineUpPromotionProductsReceipt();

        Assertions.assertThat(promotionReceipt)
                .doesNotContain("promotion3")
                .contains("promotion1").contains("1")
                .contains("promotion2").contains("2");
    }

    @Test
    @DisplayName("금액 정보를 정렬한다")
    void lineUpPurchaseInfo() {
        String purchaseInfoReceipt = receipt.lineUpPurchaseInfo();

        Assertions.assertThat(purchaseInfoReceipt)
                .contains("9")
                .contains("10,000")
                .contains("-2,000")
                .contains("-1,000")
                .contains("7,000");
    }

    @Test
    @DisplayName("총 수량이 0인지 검사한다")
    void isPurchaseNothingTest() {
        boolean purchaseNothing = receipt.isPurchaseNothing();

        Assertions.assertThat(purchaseNothing).isFalse();
    }
}