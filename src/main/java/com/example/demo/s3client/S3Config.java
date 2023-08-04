package com.example.demo.s3client;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Value("${aws.region}")
    private String awsRegion;

    private String accessKey;

    private String secretKey;

    public S3Config(){
        Dotenv dotenv = Dotenv.load();
        this.accessKey = dotenv.get("aws.access-key");
        this.secretKey = dotenv.get("aws.secret-key");
    }

    @Bean
    public S3Client s3Client(){
        return S3Client.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(this.awsCredentialsProvider())
                .build();
    }

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider(){
        AwsCredentials awsCredentials = AwsBasicCredentials.create( accessKey, secretKey);
        return new AwsCredentialsProvider() {
            @Override
            public AwsCredentials resolveCredentials() {
                return awsCredentials;
            }
        };
    }

    @Bean
    public S3Presigner s3Presigner(){
        return S3Presigner.builder().region(Region.of(awsRegion)).credentialsProvider(this.awsCredentialsProvider()).build();
    }
}
