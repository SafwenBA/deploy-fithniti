package com.team.fithniti.demo.controller.api;

import com.flickr4java.flickr.FlickrException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api
@RequestMapping("/flickr")
public interface FlickrApi {
    @ApiOperation(value = "Save photo to flickr",responseContainer = "void")
    @PostMapping("/add")
    String save(@RequestBody MultipartFile file) throws IOException, FlickrException;
}
