package com.test.util;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter; // 드디어 정상 임포트!
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUpload {

    public static String saveAsWebp(MultipartFile file, String uploadDir) throws IOException {
        if (file == null || file.isEmpty()) return null;

        // 저장 폴더 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // 파일명 생성 (UUID.webp)
        String fileName = UUID.randomUUID().toString() + ".webp";
        File destination = new File(Paths.get(uploadDir, fileName).toString());

        // Scrimage를 이용한 WebP 변환 저장
        // DEFAULT는 품질 80의 무손실에 가까운 압축을 제공합니다.
        ImmutableImage.loader()
                .fromStream(file.getInputStream())
                .output(WebpWriter.DEFAULT, destination);

        return fileName;
    }
}