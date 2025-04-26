package com.repsy.storage.impl;

import com.repsy.enums.MessageType;
import com.repsy.exception.BaseException;
import com.repsy.exception.ErrorMessage;
import com.repsy.storage.IStorageService;
import io.minio.*;
import io.minio.errors.MinioException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@ConditionalOnProperty(name = "storage.strategy", havingValue = "object-storage")
public class ObjectStorageServiceImpl implements IStorageService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @PostConstruct
    public void init() {
        System.out.println("ObjectStorageServiceImpl kullanılıyor.");
    }
    @Override
    public String deployment(String path, MultipartFile repFile, MultipartFile metaFile) throws IOException {
        try{
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(path + "/" + repFile.getOriginalFilename())
                    .contentType(repFile.getContentType())
                    .stream(repFile.getInputStream(), repFile.getSize(), -1)
                    .build());

            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(path + "/" + metaFile.getOriginalFilename())
                    .contentType(metaFile.getContentType())
                    .stream(metaFile.getInputStream(), metaFile.getSize(), -1)
                    .build());

            return "REP ve META dosyaları başarıyla yüklendi.";
        } catch (MinioException me) {
            throw new BaseException(new ErrorMessage(MessageType.MINIO_OPERATION_EXCEPTION, me.getMessage()));
        } catch (IOException ioe) {
            throw new BaseException(new ErrorMessage(MessageType.DEPLOYMENT_EXCEPTION, ioe.getMessage()));
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, e.getMessage()));
        }

    }

    @Override
    public Resource download(String packageName, String version, String fileName) {
        try {
            String objectName = packageName + "/" + version + "/" + fileName;
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return new InputStreamResource(stream);
        } catch (MinioException me) {
            throw new BaseException(new ErrorMessage(MessageType.MINIO_OPERATION_EXCEPTION, me.getMessage()));
        } catch (IOException ioe) {
            throw new BaseException(new ErrorMessage(MessageType.DOWNLOAD_EXCEPTION, ioe.getMessage()));
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, e.getMessage()));
        }
    }
}
