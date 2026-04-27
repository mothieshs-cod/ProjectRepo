package com.example.Project.Categories.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {

        if (!file.getContentType().startsWith("image")) {
            throw new RuntimeException("Only image files allowed");
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        File destination = new File(dir, fileName);

        file.transferTo(destination);

        System.out.println("Saved at: " + destination.getAbsolutePath());

        return fileName;
    }


    public void delete(String fileName) {

        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        File file = new File(uploadDir, fileName);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                throw new RuntimeException("Could not delete file: " + fileName);
            }
        }
    }
}