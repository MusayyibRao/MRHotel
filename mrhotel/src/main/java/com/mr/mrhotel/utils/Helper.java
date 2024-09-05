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

        public final String UPLOAD_DIR = "C:\\Users\\musar\\Downloads\\mrhotel\\mrhotel\\src\\main\\resources\\image\\";
//    public final String UPLOAD_DIR = new ClassPathResource("/image").getFile().getAbsolutePath();
//
//    public Helper() throws IOException {
//    }

    public boolean uploadFile(MultipartFile photo) {
        boolean f = false;
        try {
//            InputStream is = file.getInputStream();
//            byte[] data = new byte[is.available()];
//            is.read(data);
//
//            FileOutputStream fos = new FileOutputStream(UPLOAD_DIR + File.separator + file.getOriginalFilename());
//            fos.write(data);
//            fos.flush();
//            fos.close();
            Files.copy(photo.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + photo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            f = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }

}