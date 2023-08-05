package com.example.demo.auth;


import com.example.demo.config.JwtService;
import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.ortak.messages.MessageType;
import com.example.demo.token.Token;
import com.example.demo.token.TokenRepository;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Value("${application.domain}")
    private String domain;

    private Dotenv dotenv;

    public AuthService(){
        dotenv = Dotenv.load();
    }

    public String login(LoginRequest loginRequest){
        //if the otp is matched
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.phoneNum(),
                        loginRequest.otp()
                )
        );

        var user = userRepository.findByPhoneNum(loginRequest.phoneNum()).orElseThrow(()->new EntityNotFoundException("ur_user_not_found"));
        var extraClaims = new HashMap<String, Object>();
        extraClaims.put("deneme keyi", "deneme degeri");
        var jwt = jwtService.buildToken(extraClaims, user);

        var token = Token.builder()
                .user(user)
                .token(jwt)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);

        return jwt;
    }

    public String register(String phoneNum){
        //if user does not exist create new one, if does just return it
        User user;
        Optional<User> existingUser = userRepository.findByPhoneNum(phoneNum);

        //if the phoneNum is already used by a reviewer throw an error to prevent registering as a user with the same phoneNum aka username
        if(existingUser.isPresent() && existingUser.get().getRole() == Role.REVIEWER){
            throw new RuntimeException("A reviewer cannot be a user.");
        }

        user = existingUser.orElseGet(() -> new User(null, phoneNum, null, null, null, null, Role.USER));

        String otp = generateOneTimePassword();
        String encodedOtp = encodeOneTimePassword(otp);
        user.setOtp(encodedOtp);
        user.setOtpRequestedTime(new Date());

        userRepository.save(user);

        return otp;
    }

    public ResponseCookie createCookie(String jwt){
        ResponseCookie cookie = ResponseCookie.from("token", jwt)
                .sameSite("Strict")
                .secure(false)
                .maxAge(TimeUnit.MILLISECONDS.toSeconds(jwtService.getJwtExpiration()))
                .path("/")
                .domain(this.domain)
                .httpOnly(true)
                .build();

        return cookie;
    }


    public static String generateOneTimePassword() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    private String encodeOneTimePassword(String otp){
        String encodedOtp = passwordEncoder.encode(otp);

        return encodedOtp;
    }

    private ResponseEntity<String> sendOtpSms(User user, String otp){
        return ResponseEntity.ok(otp);
    }

    private void clearOtp(User user){
        user.setOtp(null);
        user.setOtpRequestedTime(null);
        userRepository.save(user);
    }


    public MessageResponse reviewerRegister(String phoneNum, String password, String creationKey) throws AccessDeniedException {

        //check for phonenum if user already exists
        if(userRepository.findByPhoneNum(phoneNum).isPresent()){
            throw new EntityExistsException("Reviewer already exists.");
        }

        if(!creationKey.equals(dotenv.get("jwt.reviewer-registration-key"))){
            throw new AccessDeniedException("Unauthorized request.");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(2050,1,1);

        String encodedPassword = encodeOneTimePassword(password);

        User reviewer = new User(null, phoneNum, null, null, encodedPassword, calendar.getTime(), Role.REVIEWER );

        userRepository.save(reviewer);

        return new MessageResponse("ur_reviewer_successfully_registered", MessageType.INFO);
    }

    public String reviewerLogin(String phoneNum, String password){
        //throws an exception if the password does not match
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        phoneNum,
                        password
                )
        );

        var extraClaims = new HashMap<String, Object>();
        var user = userRepository.findByPhoneNum(phoneNum).orElseThrow(()->new EntityNotFoundException("ur_user_not_found"));

        var jwt = jwtService.buildToken(extraClaims, user);

        var token = Token.builder()
                .user(user)
                .token(jwt)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);

        return jwt;
    }

}
