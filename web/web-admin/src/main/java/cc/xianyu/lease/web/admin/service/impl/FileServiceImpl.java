package cc.xianyu.lease.web.admin.service.impl;

import cc.xianyu.lease.common.minio.MinioProperties;
import cc.xianyu.lease.web.admin.service.FileService;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

  @Autowired
  private MinioClient minioClient;

  @Autowired
  private MinioProperties properties;

  private String CreateBucketPolicyConfig(String bucketName) {
    return """
            {
              "Statement":[
                 {
                    "Action":"s3:GetObject",
                    "Effect":"Allow",
                    "Principal":"*",
                    "Resource":"arn:aws:s3:::%s/*"
                 }
              ],
              "Version":"2012-10-17"
            }
            """.formatted(bucketName);
  }

  @Override
  public String upload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket(properties.getBucketName()).build());
    if (!b) {
      minioClient.makeBucket(MakeBucketArgs.builder().bucket(properties.getBucketName()).build());
      minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
              .bucket(properties.getBucketName())
              .config(CreateBucketPolicyConfig(properties.getBucketName()))
              .build());
      String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + UUID.randomUUID() + "/" + file.getOriginalFilename();
      minioClient.putObject(PutObjectArgs
              .builder()
              .bucket(properties.getBucketName())
              .object(fileName)
              .stream(file.getInputStream(), file.getSize(), -1)
              .contentType(file.getContentType())
              .build()
      );
      String url = String.join("/", properties.getEndpoint(), properties.getBucketName(), fileName);
      return url;
    }
    return null;
  }
}
