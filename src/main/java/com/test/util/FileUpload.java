package com.test.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.Arrays;
import java.util.List;

public class FileUpload {

    // 허용할 확장자 리스트
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");

    public static String saveFile(MultipartFile file, String uploadDir) throws IOException {
        if (file == null || file.isEmpty()) return null;

        // 1. 확장자 추출 및 검사
        String originalName = file.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf(".") + 1).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(ext)) {
            throw new IllegalArgumentException("허용되지 않는 파일 형식입니다. (jpg, png, gif만 가능)");
        }

        // 2. 저장 폴더 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // 3. 고유한 파일명 생성 (UUID)
        String savedFileName = UUID.randomUUID().toString() + "." + ext;
        File destination = new File(uploadDir, savedFileName);

        // 4. 표준 방식으로 파일 물리적 저장
        file.transferTo(destination);

        return savedFileName;
    }
}