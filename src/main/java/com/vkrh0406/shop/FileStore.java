package com.vkrh0406.shop;

import com.vkrh0406.shop.domain.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileStore {

    private String fileDir = "D:/spring/files/";

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) {

        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                uploadFiles.add(storeFile(multipartFile));
            }

        }

        return uploadFiles;
    }


    public UploadFile storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        try {
            multipartFile.transferTo(new File(getFullPath(storeFileName)));
        } catch (Exception e) {
            log.info("file upload error", e);
        }

        return new UploadFile(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        String uuid = UUID.randomUUID().toString();

        checkFileType(ext);

        String storeFileName = uuid + "." + ext;
        return storeFileName;
    }

    private void checkFileType(String ext) {
        String ext_lower = ext.toLowerCase(Locale.ROOT);
        if (ext_lower.equals("exe")) {
            throw new IllegalStateException("제한된 파일 확장자입니다.");
        }
        if (ext_lower.equals("js")) {
            throw new IllegalStateException("제한된 파일 확장자입니다.");
        }
        if (ext_lower.equals("class")) {
            throw new IllegalStateException("제한된 파일 확장자입니다.");
        }
        if (ext_lower.equals("php")) {
            throw new IllegalStateException("제한된 파일 확장자입니다.");
        }
        if (ext_lower.equals("jsp")) {
            throw new IllegalStateException("제한된 파일 확장자입니다.");
        }
        if (ext_lower.equals("sh")) {
            throw new IllegalStateException("제한된 파일 확장자입니다.");
        }
    }


}
