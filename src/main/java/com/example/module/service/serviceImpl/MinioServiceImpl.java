package com.example.module.service.serviceImpl;

import com.example.module.service.MinioService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
/**
 * Created by DIPU on 7/27/20
 */
@Service
@Log4j2
public class MinioServiceImpl implements MinioService {

    @Value("${minio.bucket.name}")
    private String defaultBucketName;

    @Autowired
    private MinioClient minioClient;

    @Override
    public boolean uploadByUpdatedAPI(String name, byte[] bytes) {
        try {

            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(defaultBucketName).object(name).stream(
                            byteArrayInputStream, bytes.length, -1)
                            .contentType("image/png")
                            .build());

            return true;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public byte[] getFileStream(String key) {

            try (InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(defaultBucketName)
                            .object(key)
                            .build())) {
                byte[] content = IOUtils.toByteArray(stream);
                return content;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    @Override
    public boolean deleteFile(String bucketName, String objectName) {
        try
        {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
            return true;
        }
        catch (Exception e)
        {
            log.error("Err-deleteFile:"+ e.getMessage());
        }
        return false;
    }
}
