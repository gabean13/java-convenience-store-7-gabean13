package store.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import store.common.FileNameConstants;

public class FileService {
    public List<String> readFile(String fileName) {
        Path path = Paths.get(FileNameConstants.FILE_DIRECTORY, fileName);
        return convertFile(path);
    }

    protected List<String> convertFile(Path path) {
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            //제일 첫번째 줄은 설명이므로 삭제
            return lines.skip(1).toList();
        } catch (IOException e) {
            // 파일 읽기 실패 시 빈 리스트 반환
            return new ArrayList<>();
        }
    }
}
