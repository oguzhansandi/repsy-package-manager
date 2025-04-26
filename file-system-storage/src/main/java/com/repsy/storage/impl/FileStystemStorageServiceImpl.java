package com.repsy.storage.impl;

import com.repsy.enums.MessageType;
import com.repsy.exception.BaseException;
import com.repsy.exception.ErrorMessage;
import com.repsy.storage.IStorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@ConditionalOnProperty(name = "storage.strategy", havingValue = "file-system-storage")
public class FileStystemStorageServiceImpl implements IStorageService {

    @Value("${storage.location}")
    private String storageRoot;

    @PostConstruct
    public void init() {
        System.out.println("FileSystemStorageServiceImpl kullanılıyor.");
    }

    @Override
    public String deployment(String path, MultipartFile repFile, MultipartFile metaFile) {
        try {
            if (!repFile.getOriginalFilename().endsWith(".rep")) {
                throw new BaseException(new ErrorMessage(MessageType.INVALID_FILE_TYPE_EXCEPTION, ".rep uzantılı dosya yüklenebilir"));
            }

            if (!"meta.json".equals(metaFile.getOriginalFilename())) {
                throw new BaseException(new ErrorMessage(MessageType.INVALID_FILE_TYPE_EXCEPTION, "meta.json yüklenebilir"));
            }

            Path targetDir = Paths.get(storageRoot).resolve(path).normalize();
            Files.createDirectories(targetDir);

            Files.copy(repFile.getInputStream(), targetDir.resolve(repFile.getOriginalFilename()).normalize(), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(metaFile.getInputStream(), targetDir.resolve(metaFile.getOriginalFilename()).normalize(), StandardCopyOption.REPLACE_EXISTING);

            return "REP ve META dosyaları başarıyla yüklendi.";

        } catch (IOException ioe) {
            throw new BaseException(new ErrorMessage(MessageType.DEPLOYMENT_EXCEPTION,  ioe.getMessage()));
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION,  e.getMessage()));
        }
    }

    @Override
    public Resource download(String packageName, String version, String fileName) {
        try {
            Path filePath = Paths.get(storageRoot)
                    .resolve(packageName)
                    .resolve(version)
                    .resolve(fileName)
                    .normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new BaseException(new ErrorMessage(MessageType.FILE_NOT_FOUND_EXCEPTION, fileName));
            }

        } catch (IOException ioe) {
            throw new BaseException(new ErrorMessage(MessageType.DOWNLOAD_EXCEPTION, ioe.getMessage()));
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.DOWNLOAD_EXCEPTION, e.getMessage()));
        }
    }
}
