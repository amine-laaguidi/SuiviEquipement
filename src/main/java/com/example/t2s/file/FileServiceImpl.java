package com.example.t2s.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Value("${certificate.upload.path}")
    private String fileUploadPath;
    @Override
    public String saveFile(MultipartFile file) throws Exception {

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String storagePath = System.getProperty("user.dir")+fileUploadPath+"_"+filename;
        Path directoryPath = Paths.get(storagePath);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        file.transferTo(new File(storagePath));
        return filename;
    }

    @Override
    public void deleteFile(String imageUrl) throws Exception {

        String storagePath = System.getProperty("user.dir") + fileUploadPath + imageUrl;
        Path imagePath = Paths.get(storagePath);
        try {
            Files.deleteIfExists(imagePath);
        } catch (Exception e) {
            throw new Exception("impossible de supprimer ce fichier");
        }
    }
}
