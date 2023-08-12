package com.example.demo.image;


import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.ortak.messages.MessageType;
import com.example.demo.s3client.S3Service;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageService {

    private S3Service s3Service;

    private ImageRepository imageRepository;

    private UserService userService;


    public MessageResponse saveImage(MultipartFile file){

        User user = userService.getCurrentlyLoggedInUser();

        String imageName = createImageName(file);

        s3Service.putImageToS3Bucket(imageName, file);


        //if no error so far create an image and save to image repo
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
//        Image image = imageRepository.findByStatus(Status.AWAITING).orElseThrow(()->new NoSuchElementException("Native Query Error in get random image. No such element."));

        Image image = imageRepository.findRandomAwaitingImage().orElseThrow(()->new NoSuchElementException("Native Query Error in get random image. No such element."));
        String accessLink = getImageAccessLink(image.getName());

        return new ImageViewModel(image.getId(), accessLink, null, null);
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


    public Optional<Image> findByName(String name){
        return imageRepository.findByName(name);
    }

}
