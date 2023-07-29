package com.example.demo.auth;

import com.example.demo.ortak.messages.MessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
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
        return authService.login(loginRequest, response);
    }
}
