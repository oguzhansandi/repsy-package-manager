package com.respy.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IPackageService {
    String deployment(MultipartFile repFile, MultipartFile metaFile, String name, String version);

    Resource download(String packageName, String version, String fileName);
}

