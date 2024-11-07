package store.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileService {
    public List<String> readProductFile() {
        Path productPath = Paths.get("src", "main", "resources", "products.md");
        return readFile(productPath);
    }

    public List<String> readPromotionFile() {
        Path promotionPath = Paths.get("src", "main", "resources", "promotions.md");
        return readFile(promotionPath);
    }

    private List<String> readFile(Path path) {
        try {
            Charset charset = StandardCharsets.UTF_8;
            List<String> file = Files.readAllLines(path, charset);
            //제일 첫번째 줄은 설명이므로 삭제
            file.removeFirst();
            return file;
        } catch (IOException e) {
            // 파일 읽기 실패 시 빈 리스트 반환
            return Collections.EMPTY_LIST;
        }
    }
}
