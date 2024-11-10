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
    @DisplayName("[예외] 파일이 존재하지 않으면 빈 리스트를 반환한다")
    void exception_FileNotFound() {
        String fileName = "nothing";
        List<String> emptyFile = fileService.readFile(fileName);
        Assertions.assertThat(emptyFile).hasSize(0);
    }
}