package com.example.demo.auth;


import com.example.demo.config.JwtService;
import com.example.demo.token.Token;
import com.example.demo.token.TokenRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

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


    public AuthResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.phoneNum(),
                        loginRequest.otp()
                )
        );

        var user = userRepository.findByPhoneNum(loginRequest.phoneNum()).orElseThrow();
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

        return AuthResponse.builder().accessToken(jwt).build();
    }

    public String register(String phoneNum){
        User user = new User(null, phoneNum, null, null);
        String otp = generateOneTimePassword(user);
        String encodedOtp = encodeOneTimePassword(otp);
        user.setOtp(encodedOtp);
        user.setOtpRequestedTime(new Date());

        userRepository.save(user);

        return otp;
    }

    private String generateOneTimePassword(User user){
        String otp = RandomString.make(8);

        return otp;
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

}
