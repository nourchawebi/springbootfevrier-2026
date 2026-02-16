package com.example.demo.services;

import com.example.demo.DAO.FileRepository;
import com.example.demo.interfaces.FileServiceInterface;
import com.example.demo.model.File;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class FileServiceImplement  implements FileServiceInterface {
    private static final Logger LOGGER =  LoggerFactory.getLogger(FileServiceImplement.class.getName());
    private final FileRepository fileRepository;

    public FileServiceImplement(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file) {
        try {
        if(! this.fileRepository.existsByFileName(file.getOriginalFilename())) {
             File newfile= new File();
             newfile.setFileName(file.getOriginalFilename());
             newfile.setContentType(file.getContentType());
             newfile.setSize(file.getSize());

                 newfile.setData(file.getBytes());
                 return new ResponseEntity<>("File uploaded successfully" + fileRepository.save(newfile), HttpStatus.CREATED);
             }
        else {
            return new ResponseEntity<>("File already exists" + file.getOriginalFilename(), HttpStatus.CONFLICT);
        }
        } catch (IOException e) {
                 LOGGER.error("error getting data from file", e.getMessage());
                return new ResponseEntity<>("Error when getting data from file", HttpStatus.BAD_REQUEST);

             }



    }

    @Override
    public ResponseEntity<?> downloadFile(String filename) {
        Optional<File> optionalfile = fileRepository.findByFileName(filename);
        if(optionalfile.isPresent()) {
            File file = optionalfile.get();
            return new ResponseEntity<>(file, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public String saveFile(MultipartFile file) {
        return "";
    }

    @Override
    public byte[] afficherfile(String filename) {
        return new byte[0];
    }
}
