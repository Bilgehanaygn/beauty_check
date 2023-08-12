package com.example.demo.image;

import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.ortak.messages.MessageType;
import com.example.demo.s3client.S3Service;
import com.example.demo.testannotations.DatabaseTest;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;


@DatabaseTest
class ImageServiceTest {

    @Autowired
    private ImageRepository imageRepository;

    private ImageService imageService;

    @Mock
    private UserService userService;

    @Mock
    private S3Service s3Service;



    User user = new User(null, "randomPhoneNum", null, null, null, null, Role.USER);

    @BeforeEach
    void initialize(){
        imageService = new ImageService(s3Service, imageRepository, userService);
    }

    @Test
    void savesImageSuccesfully(){
        MockMultipartFile file = new MockMultipartFile("file", "hello.png", "image/jpeg", "helloimage".getBytes());
        when(userService.getCurrentlyLoggedInUser()).thenReturn(user);
        when(s3Service.putImageToS3Bucket(Mockito.any(), Mockito.any())).thenReturn(true);
        MessageResponse response = imageService.saveImage(file);

        assertThat(response.message()).isEqualTo("ur_successfully_uploaded");
        assertThat(response.type()).isEqualTo(MessageType.INFO);
    }


    @Test
    void findsRandomAwaitingImageSuccessfully(){
        Image image = new Image(null, user, "randomname", null, null, null, Status.AWAITING);

        Image savedImage = imageRepository.save(image);

        when(s3Service.getTempLinkForImage(Mockito.anyString())).thenReturn("random_link");
        ImageViewModel imageViewModel = imageService.getRandomImage();

        assertThat(imageViewModel.imageLink()).isEqualTo("random_link");
        assertThat(imageViewModel.imageId()).isEqualTo(savedImage.getId());
    }

    @Test
    void doesNotFindIfNoAwaitingImageIsAvailable(){
        Image image = new Image(null, user, "randomname", null, null, null, Status.REVIEWED);
        Image savedImage = imageRepository.save(image);
        when(s3Service.getTempLinkForImage(Mockito.anyString())).thenReturn("random_link");

        assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(()->imageService.getRandomImage());
    }

}