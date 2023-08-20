package com.example.demo.image;


import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.s3client.EkleCommand;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/awaitingImages")
    public List<ImageViewModel> getAwaitingImagesForUser(){

        return service.getAwaitingImagesForUser();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/reviewedImages")
    public List<ImageViewModel> getReviewedImagesForUser(){

        return service.getReviewedImagesForUser();
    }

    @PreAuthorize("hasRole('ROLE_REVIEWER')")
    @GetMapping("/randomImage")
    public ImageViewModel getRandomImage(){

        return service.getRandomImage();
    }


    @PreAuthorize("hasRole('ROLE_REVIEWER')")
    @PutMapping("/{imageId}")
    public MessageResponse rateAnImage(@PathVariable String imageId, @RequestBody ImageRateCommand imageRateCommand ){
        return service.rateAnImage(imageId, imageRateCommand);
    }

}
