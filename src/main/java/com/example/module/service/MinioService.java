package com.example.module.service;

/**
 * Created by DIPU on 7/27/20
 */

public interface MinioService {
    boolean uploadByUpdatedAPI(String name, byte[] bytes);
    byte[] getFileStream(String key);
    boolean deleteFile(String bucketName, String objectName);

}
