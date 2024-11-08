package store.service;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.common.FileNameConstants;

class FileServiceTest {
    FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
    }

    @Test
    @DisplayName("[정상] products.md 파일을 읽는다")
    void success_readFile() {
        String fileName = FileNameConstants.PRODUCT_FILE_NAME;

        List<String> productFile = fileService.readFile(fileName);

        Assertions.assertThat(productFile).hasSize(16).contains(
                "콜라,1000,10,탄산2+1",
                "콜라,1000,10,null",
                "사이다,1000,8,탄산2+1"
        );
    }

    @Test
    @DisplayName("[정상] promotion.md 파일을 읽는다")
    void success_readPromotionFile() {
        String fileName = FileNameConstants.PROMOTION_FILE_NAME;
        List<String> promotionFile = fileService.readFile(fileName);
        Assertions.assertThat(promotionFile).hasSize(3).contains(
                "탄산2+1,2,1,2024-01-01,2024-12-31",
                "MD추천상품,1,1,2024-01-01,2024-12-31",
                "반짝할인,1,1,2024-11-01,2024-11-30"
        );
    }

    @Test
    @DisplayName("[예외] 파일이 존재하지 않으면 빈 리스트를 반환한다")
    void exception_FileNotFound() {
        String fileName = "nothing";
        List<String> emptyFile = fileService.readFile(fileName);
        Assertions.assertThat(emptyFile).hasSize(0);
    }
}