package com.example.demo.DAO;

import com.example.demo.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileRepository extends MongoRepository<File,String> {
    Optional<File> findByFileName(String fileName);
    boolean  existsByFileName(String fileName);
}
