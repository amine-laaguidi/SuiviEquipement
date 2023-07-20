package com.example.t2s.file;

import com.example.t2s.utilisateur.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
public class FilesController {
    @Value("${certificate.upload.path}")
    private String imageUploadPath;
    @GetMapping("/file/{fileName}")
    public ResponseEntity<Resource> getImage( @PathVariable String fileName) throws Exception {
        Resource fileResource;
        try {
            fileResource = new FileSystemResource(System.getProperty("user.dir") + imageUploadPath +"_"+ fileName);
            if (!fileResource.exists()) {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileResource);
    }
}