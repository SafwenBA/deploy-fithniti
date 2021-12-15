package com.team.fithniti.demo.service;


import com.team.fithniti.demo.model.Logo;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public interface ImageService {
    //Working with Binary image
    Logo loadImage(UUID uuid);
    Logo loadDefault();
    void storeImage(UUID userId, MultipartFile file);
    void deleteImage(UUID uuid);
    void storeDefault(MultipartFile file);


    //Working with Base64 String image
    Object addImage(UUID userId, MultipartFile file);
    String getImageBase64(UUID uuid);
    Image getImage(UUID uuid);

    Image getDefault();
    String getDefaultBase64();
    void addDefault(MultipartFile file);
    Image delete(UUID uuid);

    //todo - come back to this one and remove the comments
    default String toBase64(File file) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray((File) file);
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }

    default File convertMultiPartToFile(MultipartFile file ) throws IOException
    {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }
}
