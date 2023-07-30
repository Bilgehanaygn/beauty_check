package com.example.demo.auth;

import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.user.UserViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private ObjectMapper objectMapper;
    private AuthService authService;

    public AuthController(AuthService authService, ObjectMapper objectMapper){
        this.authService = authService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/register/{phoneNum}")
    public String register(@PathVariable String phoneNum){
        return authService.register(phoneNum);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        String jwt = authService.login(loginRequest);
        ResponseCookie cookie = authService.createCookie(jwt);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return AuthResponse.builder().accessToken(jwt).build();
    }
}
