package com.example.demo.DAO;

import com.example.demo.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FileRepository extends MongoRepository<File,String> {
    Optional<File> findByFileName(String fileName);
    boolean  existsByFileName(String fileName);
   /*  @Query("{'fileName': ?0}")
    Optional<File> getfilebyfilename(String fileName);
     @Query(value = "{'fileName': ?0}",exists = true)
    boolean existsByfilename(String filename);
     @Query("{'size':{$gte:  ?0, $lte: ?1}}")
     List<File> getfilesbysize(long sizemin, long sizemax);
    @Query(value = "{'fileName': ?0}",delete = true)
    void delete(String fileName);
*/
}
