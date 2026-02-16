package com.example.demo.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileServiceInterface {
      ResponseEntity<?> uploadFile(MultipartFile file);
      ResponseEntity<?> downloadFile(String filename);
      String saveFile(MultipartFile file);
      byte[] afficherfile(String filename);
}
