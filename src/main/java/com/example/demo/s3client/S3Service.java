package com.example.demo.s3client;


import java.io.IOException;
import java.time.Duration;


import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.ortak.messages.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;


@Service
public class S3Service {

    @Autowired
    private S3Client instance;

    @Autowired
    private S3Presigner presigner;

    private final String bucketName = "actualphotobucket";


    public MessageResponse putImageToS3Bucket(String imageName, MultipartFile file){
        // assume no chance of failing
        //generate an UUID image name with a trailing file format name
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(imageName+"."+file.getContentType().split("/")[1])
                .contentType(file.getContentType())
                .build();
        try{
            instance.putObject(objectRequest, RequestBody.fromBytes(file.getBytes()));
            return new MessageResponse("success", MessageType.INFO);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public String getTempLinkForImage(String imageName){
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(imageName)
                .build();
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5))
                .getObjectRequest(getObjectRequest)
                .build();


        PresignedGetObjectRequest presignedObject = presigner.presignGetObject(presignRequest);

        return presignedObject.url().toString();
    }

    public byte[] getObject(String imageName){
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(imageName)
                .build();

        try{
            return instance.getObject(getObjectRequest).readAllBytes();
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
