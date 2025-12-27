package com.aenumz.whatsapcclone.util;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class FileUtil {
    private FileUtil() {
    }

    public static byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return new byte[0];
        }
        try {
            Path file = (Path) new File(fileUrl).toPath();
            return Files.readAllBytes((java.nio.file.Path) file);

        } catch (IOException e) {
            log.warn("There is no file found in this path {}", fileUrl);
        }
        return new byte[0];
    }
}
