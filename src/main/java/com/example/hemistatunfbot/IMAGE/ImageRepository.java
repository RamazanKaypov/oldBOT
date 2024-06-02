package com.example.hemistatunfbot.IMAGE;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "image")
public interface ImageRepository extends JpaRepository <Image, String > {

    Image findByName(String name);
}
