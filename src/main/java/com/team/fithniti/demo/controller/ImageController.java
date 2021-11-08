package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.controller.api.ImageApi;
import com.team.fithniti.demo.model.Logo;
import com.team.fithniti.demo.service.ImageService;
import com.team.fithniti.demo.service.impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.Optional;
import java.util.UUID;

//TODO : Replace CrossOrigin with proxy file later!!
@RestController
@CrossOrigin(origins = "*")
public class ImageController implements ImageApi {
    @Autowired
    private ImageService imageService;

    @Override
    public Logo getByUserId(UUID userId) {
        return imageService.loadImage(userId);
    }

    @Override
    public void save(UUID userId, MultipartFile file) {
        imageService.storeImage(userId, file);
    }

    @Override
    public Logo getDefault() {
       return imageService.loadDefault();
    }

    @Override
    public void delete(UUID userId) {
        imageService.deleteImage(userId);
    }

    @Override
    public void saveDefault(MultipartFile file) {
        imageService.storeDefault(file);
    }
}
