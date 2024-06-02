package com.example.hemistatunfbot.IMAGE;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;

    public static String uploadPath;
    @PostConstruct
    public void init(){
        File file = new File("upload");
        if(!file.exists()){
            if (file.mkdir()) {
                log.info("dir created");
            }
        }
        uploadPath = file.getAbsolutePath().replace("\\", "/");
    }


    public void save(MultipartFile file) {


        String resultPathName = UUID.randomUUID() + "." + file.getOriginalFilename();

        try {
            file.transferTo(new File(uploadPath + "/" + resultPathName));

            var image = Image.builder()
                    .id(UUID.randomUUID().toString())
                    .uploadPath(resultPathName)
                    .name(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .build();

            imageRepository.save(image);

        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }

    public void saveTelegramPhoto(MultipartFile file) {
        String resultPathName = UUID.randomUUID() + "." + file.getOriginalFilename();

        try {
            file.transferTo(new File(uploadPath + "/" + resultPathName));

            Image image = new Image();
            image.setId(UUID.randomUUID().toString());
            image.setUploadPath(resultPathName);
            image.setName(file.getOriginalFilename());
            image.setContentType(file.getContentType());

            imageRepository.save(image);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save file: " + e.getMessage());
        }
    }


    public Image getById(String id) {
        return imageRepository.findById(id).orElseThrow(() ->
                new IllegalStateException(String.format("image with id: %s not found", id)));
    }

    public void delete(String imageId) {
        if (!imageRepository.existsById(imageId)) {
            throw new IllegalStateException(String.format("image with id: %s not found", imageId));
        } else {
            imageRepository.deleteById(imageId);
        }
    }



    public List<Image> getAll() {
        return imageRepository.findAll();
    }


}
