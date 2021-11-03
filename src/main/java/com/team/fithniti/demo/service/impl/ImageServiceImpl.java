package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.model.Logo;
import com.team.fithniti.demo.repository.ImageRepo;
import com.team.fithniti.demo.service.ImageService;
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

    private static final UUID DEFAULT_UUID = UUID.nameUUIDFromBytes("DEFAULT_UUID".getBytes());

    @Override
    public Logo loadImage(UUID uuid) {
        Optional<Logo> logo = this.imageRepo.findById(uuid);
        if(logo.isPresent()){
            return logo.get();
        }else{
            //TODO : Update Exceptions
            throw new IllegalStateException("Logo not found");
        }
    }

    @Override
    public Logo loadDefault() {
        Optional<Logo> defaultLogo = this.imageRepo.findById(DEFAULT_UUID);
        if(defaultLogo.isPresent()){
            return defaultLogo.get();
        }else{
            throw new IllegalStateException("Logo not found");
        }
    }

    @Override
    public void storeImage(UUID uuid, MultipartFile file) {
        try{
            Logo logo = new Logo(uuid, new Binary(BsonBinarySubType.BINARY, file.getBytes()));
            this.imageRepo.save(logo);
        }catch (IOException e){
            return;
        }
    }

    @Override
    public void deleteImage(UUID uuid) {
        this.imageRepo.deleteById(uuid);
    }
}
