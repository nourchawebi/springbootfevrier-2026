package com.example.demo.Controller;

import com.example.demo.interfaces.FileServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
public class FileController {
    private final FileServiceInterface fileService;

    public FileController(FileServiceInterface fileService) {
        this.fileService = fileService;
    }
    @PostMapping("upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file){
        return ResponseEntity.ok(fileService.uploadFile(file));
    }
}
