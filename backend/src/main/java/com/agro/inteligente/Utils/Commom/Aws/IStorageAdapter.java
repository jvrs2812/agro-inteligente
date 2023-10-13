package com.agro.inteligente.Utils.Commom.Aws;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IStorageAdapter {
    List<String> saveImage(MultipartFile[] multipartFile, String bucket);

    void deleteImage(String urlImage, String bucket);

    String uploadImage(byte[] imageBytes, String bucketName, String objectKey);
}
