package com.aenumz.whatsapcclone.service;

import jakarta.validation.Path;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.System.currentTimeMillis;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    @Value("${application.file.uploads.media-output.path}")
    private String fileUploadPath;
    public String saveFile(@NotNull MultipartFile sourceFile, @NotNull String userId) {
        final String fileUploadSubPath = "users" + File.separator + userId;
        return this.uploadFile(sourceFile, fileUploadSubPath);

    }

    private String uploadFile(@NotNull MultipartFile sourceFile, @NotNull String fileUploadSubPath) {
        final String finalUploadPath = this.fileUploadPath + File.separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);
        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdir();
            if (!folderCreated) {
                log.warn("Unable to create the directory {}.", finalUploadPath);
                return null;
            }
        }
        final String fileExtension = this.getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = finalUploadPath + File.separator + currentTimeMillis() + fileExtension;
        Path targetPath = (Path) Paths.get(targetFilePath);
        try {
            Files.write((java.nio.file.Path) targetPath, sourceFile.getBytes());
            log.info("File saved successfully to {}.", targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("File Not Saved ", e.getMessage());
        }
        return null;
    }

    private String getFileExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.isEmpty()) {
            return "";
        }

        int lastDotIndex = originalFilename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return originalFilename.substring(lastDotIndex + 1).toLowerCase();
    }
}
