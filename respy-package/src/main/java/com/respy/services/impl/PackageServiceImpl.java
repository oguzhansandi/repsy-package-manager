package com.respy.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.respy.enums.MessageType;
import com.respy.exception.BaseException;
import com.respy.exception.ErrorMessage;
import com.respy.model.Metadata;
import com.respy.repository.MetadataRespository;
import com.respy.services.IPackageService;
import com.respy.storage.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@Service
public class PackageServiceImpl implements IPackageService {

    @Autowired
    private IStorageService storageService;

    @Autowired
    private MetadataRespository metadataRespository;

    @Override
    public String deployment(MultipartFile repFile, MultipartFile metaFile, String packageName, String version) {
        try {
            String path = packageName + "/" + version;

            try {
                String metaContent = new String(metaFile.getBytes(), StandardCharsets.UTF_8);
                ObjectMapper mapper= new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(metaContent);
                System.out.println("Gelen meta.json içeriği:");
                System.out.println(metaContent);
                Metadata meta= new Metadata();

                meta.setName(jsonNode.get("name").asText());
                meta.setAuthor(jsonNode.get("author").asText());
                meta.setVersion(jsonNode.get("version").asText());
                meta.setRawJson(metaContent);

                metadataRespository.save(meta);
            } catch (Exception e) {
                throw new BaseException(new ErrorMessage(MessageType.INVALID_META_FILE_EXCEPTION, e.getMessage()));
            }
            return storageService.deployment(path, repFile, metaFile);
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.DEPLOYMENT_EXCEPTION, e.getMessage()));
        }
    }

    @Override
    public Resource download(String packageName, String version, String fileName) {
        try {
            return storageService.download(packageName, version, fileName);
        } catch (Exception e){
            throw new BaseException(new ErrorMessage(MessageType.DOWNLOAD_EXCEPTION, e.getMessage()));
        }
    }
}
