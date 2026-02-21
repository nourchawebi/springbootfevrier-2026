package com.example.demo.Controller;

import com.example.demo.interfaces.FileServiceInterface;
import com.example.demo.model.File;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLConnection;

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
    @GetMapping("download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable("filename") String filename){
        ResponseEntity<?> response = fileService.downloadFile(filename);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            File file =(File) response.getBody();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+file.getFileName()+"\"")
                    .body(file.getData());
        }else {
            return new ResponseEntity<>("file not found",HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("uploadfolder")
    public ResponseEntity<?> uploadFolder(@RequestParam("file") MultipartFile file){
       String savedFilename= this.fileService.saveFile(file);
       return ResponseEntity.ok().body("file saved with name:"+ savedFilename);
    }
    @GetMapping("folder/{filename}")
    public  ResponseEntity<byte[]> downloadfolder(@PathVariable String filename) {
        byte[] fileData= fileService.afficherfile(filename);
        String mimeType= URLConnection.guessContentTypeFromName(filename);
        if(mimeType==null){
            mimeType= MediaType.APPLICATION_OCTET_STREAM.toString();
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).body(fileData);
    }
}