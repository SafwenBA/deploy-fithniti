package com.team.fithniti.demo.service;

import com.team.fithniti.demo.model.Logo;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageService {
    Logo loadImage(UUID uuid);
    Logo loadDefault();
    void storeImage(UUID uuid, MultipartFile file);
    void deleteImage(UUID uuid);
}
