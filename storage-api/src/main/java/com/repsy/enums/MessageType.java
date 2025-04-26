package com.repsy.enums;

import lombok.Getter;

@Getter
public enum MessageType {

    DEPLOYMENT_EXCEPTION("1000","dosya yüklenirken hata oluştu" ),
    DOWNLOAD_EXCEPTION("1001", "dosya indirilirken hata oluştu"),
    INVALID_FILE_TYPE_EXCEPTION("1002", "geçersiz dosya tipi"),
    INVALID_META_FILE_EXCEPTION("1003", "geçersiz meta.json"),
    FILE_NOT_FOUND_EXCEPTION("1004","dosya bulunamadı"),
    MINIO_OPERATION_EXCEPTION("1007", "minIO işlem hatası"),
    GENERAL_EXCEPTION("9999", "genel bir hata oluştu");


    private String code;

    private String message;

    MessageType(String code, String message) {

        this.code= code;
        this.message = message;
    }
}
