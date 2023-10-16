package com.agro.inteligente.Utils.Commom.Aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class StorageAdapter implements IStorageAdapter{

    @Value("${aws_access}")
    private String accessKey;

    @Value("${aws_secret}")
    private String secretKey;

    @Override
    public List<String> saveImage(MultipartFile[] multipartFile, String bucket) {
        AmazonS3 _client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))).withRegion(Regions.SA_EAST_1).build();
        List<String> urls = new ArrayList<String>();

        Arrays.asList(multipartFile).stream().forEach(file ->{
            try {
                InputStream input = new ByteArrayInputStream(file.getBytes());
                String url = UUID.randomUUID().toString()+"."+ FilenameUtils.getExtension(file.getOriginalFilename());

                ObjectMetadata data = new ObjectMetadata();

                data.setContentType(FilenameUtils.getExtension(file.getOriginalFilename()));


                PutObjectRequest _obj = new PutObjectRequest(bucket, url, input, data);

                _client.putObject(_obj);

                URL request = _client.getUrl(bucket, url);

                url = request.getProtocol() + "://"+ request.getHost() + request.getFile();
                urls.add(url);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return urls;
    }

    @Override
    public void deleteImage(String urlImage, String bucket) {
        AmazonS3 _client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))).withRegion(Regions.SA_EAST_1).build();
        _client.deleteObject(bucket, FilenameUtils.getName(urlImage));
    }

    public String uploadImage(byte[] imageBytes, String bucketName, String objectKey) {
        AmazonS3 _client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))).withRegion(Regions.SA_EAST_1).build();

        InputStream input = new ByteArrayInputStream(imageBytes);
        String url = UUID.randomUUID().toString();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("text/plain");

        PutObjectRequest _obj = new PutObjectRequest(bucketName, url, input, metadata);

        _client.putObject(_obj);

        URL request = _client.getUrl(bucketName, url);

        url = request.getProtocol() + "://"+ request.getHost() + request.getFile();
        return url;
    }
}
