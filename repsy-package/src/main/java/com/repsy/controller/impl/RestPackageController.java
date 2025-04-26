package com.repsy.controller.impl;

import com.repsy.controller.IRestPackageController;
import com.repsy.controller.RestBaseController;
import com.repsy.controller.RootEntity;
import com.repsy.services.IPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/package")
public class RestPackageController extends RestBaseController implements IRestPackageController {

    @Autowired
    private IPackageService packageService;

    @Override
    @PostMapping(path = "/{packageName}/{version}")
    public RootEntity<String> deployment(
            @RequestParam("packageFile") MultipartFile repFile,
            @RequestParam("metaFile") MultipartFile metaFile,
            @PathVariable String packageName,
            @PathVariable String version
    ) {
        return ok(packageService.deployment(repFile, metaFile, packageName, version));
    }

    @Override
    @GetMapping("/{packageName}/{version}/{fileName}")
    public ResponseEntity<Resource> download(
            @PathVariable String packageName,
            @PathVariable String version,
            @PathVariable String fileName) {

        Resource resource = packageService.download(packageName, version, fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}

