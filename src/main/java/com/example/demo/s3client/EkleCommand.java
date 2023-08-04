package com.example.demo.s3client;

import org.springframework.web.multipart.MultipartFile;

public record EkleCommand(MultipartFile file) {
}
