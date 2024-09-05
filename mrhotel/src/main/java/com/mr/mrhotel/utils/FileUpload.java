package com.mr.mrhotel.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUpload {


    public static ResponseEntity<String> fileUpload(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please upload file");
        }
        if (!file.getContentType().equals("image/jpeg") || !file.getContentType().equals("image/png")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please upload image in jpeg format");
        }
        boolean f = Helper.uploadFile(file);
        String UPLOAD_DIR = new ClassPathResource("/image").getFile().getAbsolutePath()+"\\"+file.getOriginalFilename();
          return ResponseEntity.ok().body("successfully");
    }

}
