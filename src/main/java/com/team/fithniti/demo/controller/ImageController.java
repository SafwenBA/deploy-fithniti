package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.controller.api.ImageApi;
import com.team.fithniti.demo.model.Image;
import com.team.fithniti.demo.model.Logo;
import com.team.fithniti.demo.service.ImageService;
import com.team.fithniti.demo.service.impl.FlickrServiceImpl;
import com.team.fithniti.demo.service.impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

//TODO : Replace CrossOrigin with proxy file later!!
@RestController
@CrossOrigin(origins = "*")
public class ImageController implements ImageApi {
    @Autowired
    private ImageService imageService;

    @Autowired
    private FlickrServiceImpl flickrService;

    @Override
    public Image getByUserId(UUID userId) {
        //return imageService.loadImage(userId);
        return imageService.getImage(userId);
    }

    @Override
    public Image save(UUID userId, MultipartFile file) {
        //imageService.storeImage(userId, file);
         return (Image) imageService.addImage(userId, file);
    }

    @Override
    public Image getDefault() {
       //return imageService.loadDefault();
        return imageService.getDefault();
    }

    @Override
    public Image delete(UUID userId) {
        return imageService.delete(userId);
    }

    @Override
    public void saveDefault(MultipartFile file) {
        imageService.addDefault(file);
    }

}
