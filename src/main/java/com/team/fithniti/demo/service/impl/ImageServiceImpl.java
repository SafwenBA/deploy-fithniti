package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.dto.request.NewImage;
import com.team.fithniti.demo.dto.request.NewLogo;
import com.team.fithniti.demo.model.Image;
import com.team.fithniti.demo.model.Logo;
import com.team.fithniti.demo.repository.ImageRepo;
import com.team.fithniti.demo.repository.LogoRepo;
import com.team.fithniti.demo.service.ImageService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

// TODO: FLickr API // MongoDB base64 String
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private LogoRepo logoRepo;

    @Autowired
    private ImageRepo imageRepo;

    private static final String DEFAULT_ID = "DEFAULT_LOGO_ID_STRING";
    private static final UUID DEFAULT_UUID = UUID.nameUUIDFromBytes("DEFAULT_UUID".getBytes());

    @Override
    public Logo loadImage(UUID uuid) {
        Optional<Logo> logo = this.logoRepo.findByUserId(uuid);
        if(logo.isPresent()){
            return logo.get();
        }else{
            //TODO : Update Exceptions
            throw new IllegalStateException("Logo not found");
        }
    }

    @Override
    public Logo loadDefault() {
        Optional<Logo> defaultLogo = this.logoRepo.findById(DEFAULT_ID);
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
            this.logoRepo.save(Logo.builder()
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
        this.logoRepo.delete(logo);
    }

    @Override
    public void storeDefault(MultipartFile file) {
        try{
            NewLogo newLogo = new NewLogo(DEFAULT_UUID, new Binary(BsonBinarySubType.BINARY, file.getBytes()));
            this.logoRepo.save(Logo.builder()
                    .id(DEFAULT_ID)
                    .userId(newLogo.getUserId())
                    .image(newLogo.getImage())
                    .build());
        }catch (IOException e){
            return;
        }
    }

    //Working with base64 String
    @Override
    public Image addImage(UUID userId, MultipartFile file) {
        try{
            NewImage newImage = new NewImage(userId, toBase64(convertMultiPartToFile(file)));
            Image image = Image.builder()
                    .userId(userId)
                    .image(newImage.getImage())
                    .build();
            this.imageRepo.save(image);
            return image;
        }catch (IOException e){
            return null;
        }
    }

    @Override
    public Image getDefault() {
        Optional<Image> image = this.imageRepo.findByUserId(DEFAULT_UUID);
        if(image.isPresent()){
            return image.get();
        }else{
            //TODO : Update Exceptions
            throw new IllegalStateException("Logo not found");
        }
    }

    @Override
    public String getImageBase64(UUID uuid) {
        Optional<Image> image = this.imageRepo.findByUserId(uuid);
        if(image.isPresent()){
            return image.get().getImage();
        }else{
            //TODO : Update Exceptions
            throw new IllegalStateException("Logo not found");
        }
    }



    @Override
    public String getDefaultBase64() {
        Optional<Image> defaultLogo = this.imageRepo.findById(DEFAULT_ID);
        if(defaultLogo.isPresent()){
            return defaultLogo.get().getImage();
        }else{
            throw new IllegalStateException("Logo not found");
        }
    }

    @Override
    public void addDefault(MultipartFile file) {
        try{
            NewImage newImage = new NewImage(DEFAULT_UUID, toBase64(convertMultiPartToFile(file)));
            this.imageRepo.save(Image.builder()
                    .id(DEFAULT_ID)
                    .userId(newImage.getUserId())
                    .image(newImage.getImage())
                    .build());
        }catch (IOException e){
            return;
        }
    }

    @Override
    public Image getImage(UUID uuid) {
        Optional<Image> image = this.imageRepo.findByUserId(uuid);
        if(image.isPresent()){
            return image.get();
        }else{
            //TODO : Update Exceptions
            throw new IllegalStateException("Logo not found");
        }
    }
    
    @Override
    public Image delete(UUID uuid) {
        Image image = getImage(uuid);
        this.imageRepo.delete(image);
        return image;
    }

}
