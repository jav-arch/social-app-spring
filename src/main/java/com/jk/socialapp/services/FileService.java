package com.jk.socialapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    public String uploadImage(String path, MultipartFile file) throws IOException;


}
