package com.example.demo.image;


import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.ortak.messages.MessageType;
import com.example.demo.s3client.S3Service;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserService userService;

    public MessageResponse saveImage(MultipartFile file){

        User user = userService.getCurrentlyLoggedInUser();

        String imageName = createImageName(file);

        s3Service.putImageToS3Bucket(imageName, file);


        //if no error so far create image and save to image repo
        Image image = new Image(null, user, imageName, null, null);

        imageRepository.save(image);

        return new MessageResponse("ur_successfully_uploaded", MessageType.INFO);
    }

    private String createImageName(MultipartFile file){
        //create uuid concatenate with image type
        StringBuilder imageNameBuilder = new StringBuilder();
        String randomId = UUID.randomUUID().toString();
        String imageTypeName = file.getContentType().split("/")[1];
        imageNameBuilder.append(randomId).append(imageTypeName);

        return imageNameBuilder.toString();
    }


    public String getImageAccessLink(String imageName){
        User user = userService.getCurrentlyLoggedInUser();

        Image image = imageRepository.findByName(imageName);

        if(image.getUser().equals(user)){
            throw new RuntimeException("ur_unauthorized_request");
        }

        return s3Service.getTempLinkForImage(imageName);
    }

}
