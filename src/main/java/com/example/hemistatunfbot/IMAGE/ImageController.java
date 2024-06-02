package com.example.hemistatunfbot.IMAGE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class ImageController {

    @Autowired
    ImageService imageService;

    @PostMapping("/api/image")
    public ResponseEntity<String> save(@RequestParam("file") MultipartFile file){
        imageService.save(file);

        return ResponseEntity.ok("saved");
    }
}
