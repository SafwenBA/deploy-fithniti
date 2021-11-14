package com.team.fithniti.demo.controller.api;

import com.flickr4java.flickr.FlickrException;
import com.team.fithniti.demo.model.Image;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Api
@RequestMapping("/flickr")
public interface FlickrApi {
    @ApiOperation(value = "Save photo to flickr",responseContainer = "void")
    @PostMapping("/add")
    String save(@RequestBody MultipartFile file) throws IOException, FlickrException;
}
