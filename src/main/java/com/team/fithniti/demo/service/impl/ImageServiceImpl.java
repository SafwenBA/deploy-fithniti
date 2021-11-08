package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.dto.request.NewLogo;
import com.team.fithniti.demo.model.Logo;
import com.team.fithniti.demo.repository.ImageRepo;
import com.team.fithniti.demo.service.ImageService;
import lombok.extern.java.Log;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

// TODO: FLickr API // MongoDB base64 String
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepo imageRepo;

    private static final String DEFAULT_ID = "DEFAULT_LOGO_ID_STRING";
    private static final UUID DEFAULT_UUID = UUID.nameUUIDFromBytes("DEFAULT_UUID".getBytes());

    @Override
    public Logo loadImage(UUID uuid) {
        Optional<Logo> logo = this.imageRepo.findByUserId(uuid);
        if(logo.isPresent()){
            return logo.get();
        }else{
            //TODO : Update Exceptions
            throw new IllegalStateException("Logo not found");
        }
    }

    @Override
    public Logo loadDefault() {
        Optional<Logo> defaultLogo = this.imageRepo.findById(DEFAULT_ID);
        if(defaultLogo.isPresent()){
            return defaultLogo.get();
        }else{
            throw new IllegalStateException("Logo not found");
        }
    }

    @Override
    public void storeImage(UUID userId, MultipartFile file) {
        try{
            NewLogo newLogo = new NewLogo(userId, new Binary(BsonBinarySubType.BINARY, file.getBytes()));
            this.imageRepo.save(Logo.builder()
                    .userId(newLogo.getUserId())
                    .image(newLogo.getImage())
                    .build());
        }catch (IOException e){
            return;
        }
    }

    @Override
    public void deleteImage(UUID uuid) {
        Logo logo = loadImage(uuid);
        this.imageRepo.delete(logo);
    }

    @Override
    public void storeDefault(MultipartFile file) {
        try{
            NewLogo newLogo = new NewLogo(DEFAULT_UUID, new Binary(BsonBinarySubType.BINARY, file.getBytes()));
            this.imageRepo.save(Logo.builder()
                    .id(DEFAULT_ID)
                    .userId(newLogo.getUserId())
                    .image(newLogo.getImage())
                    .build());
        }catch (IOException e){
            return;
        }
    }
}
