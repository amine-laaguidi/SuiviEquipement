package com.example.t2s.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String saveFile(MultipartFile file) throws Exception;
    void deleteFile(String imageUrl) throws Exception;
}
