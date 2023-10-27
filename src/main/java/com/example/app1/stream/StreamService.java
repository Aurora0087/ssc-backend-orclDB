package com.example.app1.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StreamService {

    @Autowired
    private ResourceLoader loader;

    private static final String videoFormat = "classpath:static/video/%s";

    private static final String thumbnailFormat="classpath:static/image/thumbnail/%s";

    public ResponseEntity<Resource> getFile(String fileName,String fileType) {

        String FORMAT= null;
        if (fileType=="thumbnail"){
            FORMAT=thumbnailFormat;
        } else if (fileType=="video") {
            FORMAT=videoFormat;
        }
        try {
            String filePath = String.format(FORMAT, fileName);
            Resource File = loader.getResource(filePath);

            if (File.exists()) {
                return ResponseEntity.ok().body(File);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.notFound().build();
        }
    }
}
