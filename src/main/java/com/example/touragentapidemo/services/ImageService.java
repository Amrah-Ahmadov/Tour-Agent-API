package com.example.touragentapidemo.services;

import com.example.touragentapidemo.utils.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ImageService {

    final
    FileUploadUtil fileService;

    public ImageService(FileUploadUtil fileService) {
        this.fileService = fileService;
    }

    public String uploadImageToFireBase(File file) {

        return fileService.upload(file);
    }
}
