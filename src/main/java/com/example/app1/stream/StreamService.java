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

    private static final String format = "classpath:video/%s.mp4";

    public ResponseEntity<Resource> getVideo(String videoName) {
        try {
            Resource videoFile = loader.getResource(String.format(format,videoName));
        return ResponseEntity.ok().body(videoFile);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
