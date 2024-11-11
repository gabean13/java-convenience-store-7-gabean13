package store.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileServiceTest {
    FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
    }

    @Test
    @DisplayName("파일이 존재하지 않으면 빈 리스트를 반환한다")
    void exception_FileNotFound() {
        String fileName = "nothing";
        List<String> emptyFile = fileService.readFile(fileName);
        Assertions.assertThat(emptyFile).hasSize(0);
    }

    @Test
    @DisplayName("파일을 한줄 씩 읽어 리스트를 반환한다")
    void success_FileRead(@TempDir Path tempPath) throws IOException {
        //given
        Path filePath = tempPath.resolve("file.txt");
        List<String> contents = List.of(
                "skip first line",
                "second line",
                "third line"
        );
        Files.write(filePath, contents);

        //when
        List<String> lines = fileService.convertFile(filePath);

        //then
        Assertions.assertThat(lines)
                .hasSize(2)
                .contains("second line", "third line");
    }
}