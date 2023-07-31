package com.example.demo.user;

import com.example.demo.auth.LoginRequest;
import com.example.demo.config.JwtService;
import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.ortak.messages.MessageType;
import com.example.demo.token.Token;
import com.example.demo.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.bytebuddy.utility.RandomString;
import java.util.Date;
import java.util.HashMap;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserViewModel getLoggedInUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof String){
            return null;
        }

        return ((User)principal).entityToViewModel();
    }

    private String generateOneTimePassword(User user){
        String otp = RandomString.make(8);

        return otp;
    }

    private String encodeOneTimePassword(String otp){
        String encodedOtp = this.passwordEncoder.encode(otp);

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

}
