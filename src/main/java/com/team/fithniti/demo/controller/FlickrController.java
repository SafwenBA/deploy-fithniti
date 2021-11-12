package com.team.fithniti.demo.controller;

import com.flickr4java.flickr.FlickrException;
import com.team.fithniti.demo.controller.api.FlickrApi;
import com.team.fithniti.demo.service.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
public class FlickrController implements FlickrApi {

    @Autowired
    private FlickrService flickrService;

    @Override
    public String save(MultipartFile file) throws IOException, FlickrException {
        return flickrService.savePhoto(file.getInputStream(), file.getName()    );
    }
}
