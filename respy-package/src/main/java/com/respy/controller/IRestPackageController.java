package com.respy.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IRestPackageController {

    public RootEntity<String> deployment(MultipartFile repFile, MultipartFile metaFile, String packageName, String version);

    public ResponseEntity<Resource> download(String packageName, String version, String fileName);

}
