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
        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please upload image in jpeg format");
        }
        System.out.println("file Path:=");
        String UPLOAD_DIR = new ClassPathResource("/image").getFile().getAbsolutePath()+"\\"+file.getOriginalFilename();
        System.out.println("file Path:="+UPLOAD_DIR);
          return ResponseEntity.ok().body(UPLOAD_DIR);
    }

}
