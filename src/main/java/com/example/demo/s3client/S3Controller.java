package com.example.demo.s3client;


import com.example.demo.ortak.messages.MessageResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/s3")
public class S3Controller {

    private S3Service service;

    public S3Controller(S3Service service){
        this.service=service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse putObject(EkleCommand command) throws IOException{
        //checkIfCustomerExistsOrThrow(customerId);
        //String profileImageId = UUID.randomUUID().toString();

        return service.putImageToS3Bucket("21312321555", command.file());
    }

    @GetMapping(value="/{imageId}")
    public String getTempLink(@PathVariable String imageId){
        return service.getTempLinkForImage(imageId);
    }


}
