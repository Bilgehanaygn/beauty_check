package com.example.demo.image;



import com.example.demo.BaseTest;
import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.ortak.messages.MessageType;
import com.example.demo.s3client.S3Service;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


public class DemoTests extends BaseTest {


    @Mock
    private UserService userService;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private ImageService imageService;

    @Mock
    private ImageRepository imageRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    String randomPhoneNum = "randomphonenum";
    String randomPassword = "randompassword";
    User user = new User(null, randomPhoneNum, null, null, randomPassword, null, Role.USER);

    @BeforeAll
    public static void initialize(){

//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(randomPhoneNum, randomPassword);
//        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @Test
    public void demo(){
        String answer = imageService.testFunction();
        assertThat("1").isEqualTo(answer);
    }

    @Test
    public void savesImageSuccessfullyForTheCurrentAuthenticatedUser()throws Exception{
        MockMultipartFile file = new MockMultipartFile("file", "hello.txt", "image/jpeg", "hello content".getBytes());

        when(userService.getCurrentlyLoggedInUser()).thenReturn(user);
        when(s3Service.putImageToS3Bucket(Mockito.any(String.class), Mockito.any(MultipartFile.class))).thenReturn(true);
        when(s3Service.getTempLinkForImage(Mockito.any(String.class))).thenReturn("randomLink");

        MessageResponse messageResponse = imageService.saveImage(file);

        Mockito.verify(userService).getCurrentlyLoggedInUser();

        //fetch the saved image
        ImageViewModel fetchImageMessageResponse = imageService.getRandomImage();

        assertThat(messageResponse.type()).isEqualTo(MessageType.INFO);
        assertThat(messageResponse.message()).isEqualTo("ur_successfully_uploaded");
        assertThat(fetchImageMessageResponse.imageLink()).isEqualTo("randomLink");
        System.out.println(fetchImageMessageResponse.imageId());
    }



}
