package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.bytebuddy.utility.RandomString;
import java.util.Date;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<String> generateOneTimePassword(User user){
        String otp = RandomString.make(8);
        String encodedOtp = passwordEncoder.encode(otp);

        user.setOtp(encodedOtp);
        user.setOtpRequestedTime(new Date());

        userRepository.save(user);

        return sendOtpSms(user, otp);
    }

    public ResponseEntity<String> sendOtpSms(User user, String otp){
        return ResponseEntity.ok(otp);
    }

    public void clearOtp(User user){
        user.setOtp(null);
        user.setOtpRequestedTime(null);
        userRepository.save(user);
    }

}
