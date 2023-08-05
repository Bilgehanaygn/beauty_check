package com.example.demo.image;


import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.s3client.EkleCommand;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private ImageService service;

    public ImageController(ImageService service){
        this.service=service;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MessageResponse putObject(EkleCommand command){

        return service.saveImage(command.file());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REVIEWER', 'ROLE_USER')")
    @GetMapping(value="/{imageId}")
    public String getTempLink(@PathVariable String imageId){
        return service.getImageAccessLink(imageId);
    }
}
