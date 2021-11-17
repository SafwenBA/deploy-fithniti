package com.team.fithniti.demo.service.impl;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;
import com.flickr4java.flickr.uploader.UploadMetaData;
import com.team.fithniti.demo.service.FlickrService;
import com.team.fithniti.demo.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class FlickrServiceImpl implements FlickrService {
    private String apiKey = "4e21ac9d4f0e38aa342a1930a15bcde0";
    private String apiSecret="47b504260ab84a04";
    //got from the link
    private String appKey = "72157720822071678-6750eef9a415f37f";
    private String appSecret = "22610d126339fc65";

    @Autowired
    private Flickr flickr;

    @Override
    public String savePhoto(InputStream photo, String title) throws FlickrException {
        UploadMetaData uploadMetaData = new UploadMetaData();
        uploadMetaData.setTitle(title);

        Auth auth = new Auth();
        auth.setPermission(Permission.DELETE);
        auth.setToken(appKey);
        auth.setTokenSecret(appSecret);
        RequestContext.getRequestContext().setAuth(auth);

        String photoUrl = flickr.getUploader().upload(photo, uploadMetaData);
        return  flickr.getPhotosInterface().getPhoto(photoUrl).getMedium640Url();
    }

    @Override
    public String getDefaultLogo() {
        return Constants.defaultLogo;
    }
}
