package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.model.Image;
import com.team.fithniti.demo.model.Logo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Api
@RequestMapping("/image")
public interface ImageApi {

    @ApiOperation(value = "Get Logo By Id",responseContainer = "Logo")
    @GetMapping("/{userId}")
    Image getByUserId(@PathVariable UUID userId);

    @ApiOperation(value = "Save Logo",responseContainer = "void")
    @PostMapping("/add/{userId}")
    Image save(@PathVariable UUID userId, @RequestBody MultipartFile file);

    @ApiOperation(value = "Get Default Logo",responseContainer = "Logo")
    @GetMapping("")
    Image getDefault();

    @ApiOperation(value = "Delete Logo By Id",responseContainer = "void")
    @DeleteMapping("/{userId}")
    Image delete(@PathVariable UUID userId);

    @ApiOperation(value = "Save default Logo",responseContainer = "void")
    @PostMapping("/default")
    void saveDefault(@RequestBody MultipartFile file);

}
