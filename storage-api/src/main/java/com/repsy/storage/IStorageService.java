package com.repsy.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface IStorageService {
    String deployment(String path, MultipartFile repFile, MultipartFile metaFile) throws IOException;

    Resource download(String packageName, String version, String fileName) throws IOException;
}
