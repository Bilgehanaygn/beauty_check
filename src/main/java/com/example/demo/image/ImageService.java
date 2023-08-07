package com.example.demo.image;


import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.ortak.messages.MessageType;
import com.example.demo.s3client.S3Service;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        Image image = new Image(null, user, imageName, null, null, null, null);

        imageRepository.save(image);

        return new MessageResponse("ur_successfully_uploaded", MessageType.INFO);
    }

    private String createImageName(MultipartFile file){
        //create uuid concatenate with image type
        StringBuilder imageNameBuilder = new StringBuilder();
        String randomId = UUID.randomUUID().toString();
        String imageTypeName = file.getContentType().split("/")[1];
        imageNameBuilder.append(randomId).append(".").append(imageTypeName);

        return imageNameBuilder.toString();
    }


    private String getImageAccessLink(String imageName){
//        User user = userService.getCurrentlyLoggedInUser();
//
//        Image image = imageRepository.findByName(imageName).orElseThrow(()->new EntityNotFoundException("ur_image_not_found"));
//
//        if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")) &&  !image.getUser().equals(user)){
//            throw new AccessDeniedException("ur_unauthorized_request");
//        }

        return s3Service.getTempLinkForImage(imageName);
    }


    public ImageViewModel getRandomImage(){
        Image image = imageRepository.findRandomAwaitingImage().orElseThrow(()->new RuntimeException("Native Query Error in get random image"));
        String accessLink = getImageAccessLink(image.getName());

        return new ImageViewModel(image.getId().toString(), accessLink, null, null);
    }


    @Transactional
    public MessageResponse rateAnImage(String imageId, ImageRateCommand imageRateCommand){
        Image image = imageRepository.findById(Long.valueOf(imageId)).orElseThrow(()->new EntityNotFoundException("ur_image_not_found"));

        image.setTags(
                imageRateCommand.tags().stream().map(Tag::valueOf).toList()
        );

        image.setPoint(Point.valueOf(imageRateCommand.point()));

//        imageRepository.save(image); throws error if transactional annotation is not included.

        return new MessageResponse("ur_successfully_reviewed", MessageType.INFO);
    }

}
