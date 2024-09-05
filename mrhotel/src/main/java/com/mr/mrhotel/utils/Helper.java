package com.mr.mrhotel.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class Helper {


    public static final String UPLOAD_DIR;

    static {
        try {
            UPLOAD_DIR = new ClassPathResource("/image").getFile().getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Helper() throws IOException {
    }

    public static  boolean uploadFile(MultipartFile file) {
        boolean f = false;
        try {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            f = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }

}