package store.service;

import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.Product;
import store.model.PurchasedProduct;
import store.model.Receipt.PurchaseInfo;
import store.model.Stock;

class ReceiptServiceTest {

    ReceiptService receiptService;
    Map<String, Stock> stocks;
    List<PurchasedProduct> purchasedProducts;
    List<PurchasedProduct> promotionProducts;

    @BeforeEach
    void setUp()  {
        receiptService = new ReceiptService();

        Stock productWithPromo1 = new Stock(new Product("productWithPromo1", 10, "testPromo1"));
        productWithPromo1.setGeneralQuantity(10);
        productWithPromo1.setPromotionQuantity(10);
        Stock productWithoutPromo = new Stock(new Product("productWithoutPromo", 20, "null"));
        productWithoutPromo.setGeneralQuantity(10);
        productWithoutPromo.setPromotionQuantity(0);
        Stock productWithPromo2 = new Stock(new Product("productWithPromo2", 30, "testPromo2"));
        productWithPromo2.setGeneralQuantity(10);
        productWithPromo2.setPromotionQuantity(0);
        stocks = Map.of(
                "productWithPromo1", productWithPromo1,
                "productWithoutPromo", productWithoutPromo,
                "productWithPromo2", productWithPromo2
        );

        PurchasedProduct product1 = new PurchasedProduct("productWithPromo1", 5);
        PurchasedProduct product2 = new PurchasedProduct("productWithPromo2", 5);
        PurchasedProduct productPromo1 = new PurchasedProduct("productWithoutPromo", 10);
        PurchasedProduct productPromo2 = new PurchasedProduct("productWithoutPromo", 5);
        purchasedProducts = List.of(product1, product2, productPromo1);
        promotionProducts = List.of(productPromo2);
    }

    @Test
    @DisplayName("각 일반 상품들의 총 가격을 계산한다")
    void updateProductsPrice_general() {
        List<PurchasedProduct> updatedProductsPrices = receiptService.updateProductsPrice(stocks, purchasedProducts);

        PurchasedProduct product1 = updatedProductsPrices.get(0);
        Assertions.assertThat(product1.getTotalPrice()).isEqualTo(50);
        PurchasedProduct product2 = updatedProductsPrices.get(1);
        Assertions.assertThat(product2.getTotalPrice()).isEqualTo(150);
        PurchasedProduct productPromo = updatedProductsPrices.get(2);
        Assertions.assertThat(productPromo.getTotalPrice()).isEqualTo(200);
    }

    @Test
    @DisplayName("각 프로모션 상품들의 총 가격을 계산한다")
    void updateProductPrice_promotion() {
        List<PurchasedProduct> promoProductPrices = receiptService.updateProductsPrice(stocks, promotionProducts);

        PurchasedProduct product1 = promoProductPrices.get(0);
        Assertions.assertThat(product1.getTotalPrice()).isEqualTo(100);
    }

    @Test
    @DisplayName("멤버십 할인을 받는 PurchaseInfo를 계산한다")
    void updatePurchaseInfoWithPromotion() {
        PurchaseInfo purchaseInfo = receiptService.updatePurchaseInfo(stocks,
                receiptService.updateProductsPrice(stocks, promotionProducts),
                receiptService.updateProductsPrice(stocks, purchasedProducts), true);

        Assertions.assertThat(purchaseInfo)
                .hasFieldOrPropertyWithValue("totalQuantity", 20)
                .hasFieldOrPropertyWithValue("totalPrice", 400)
                .hasFieldOrPropertyWithValue("promotionDiscountPrice", 100)
                .hasFieldOrPropertyWithValue("membershipDiscountPrice", 60)
                .hasFieldOrPropertyWithValue("finalPrice", 240);

    }

    @Test
    @DisplayName("멤버십 할인을 받지않는 PurchaseInfo를 계산한다")
    void updatePurchaseInfoWithoutPromotion() {
        PurchaseInfo purchaseInfo = receiptService.updatePurchaseInfo(stocks,
                receiptService.updateProductsPrice(stocks, promotionProducts),
                receiptService.updateProductsPrice(stocks, purchasedProducts), false);

        Assertions.assertThat(purchaseInfo)
                .hasFieldOrPropertyWithValue("totalQuantity", 20)
                .hasFieldOrPropertyWithValue("totalPrice", 400)
                .hasFieldOrPropertyWithValue("promotionDiscountPrice", 100)
                .hasFieldOrPropertyWithValue("membershipDiscountPrice", 0)
                .hasFieldOrPropertyWithValue("finalPrice", 300);
    }

}