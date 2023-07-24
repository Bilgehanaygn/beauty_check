package com.example.demo.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private ObjectMapper objectMapper;
    private UserService userService;

    public UserController(UserService userService, ObjectMapper objectMapper){
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/register/{phoneNum}")
    public ResponseEntity<String> register(@PathVariable String phoneNum, HttpServletResponse response){
        User user = new User(null, phoneNum, null, null);
        return userService.generateOneTimePassword(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok("success login");
    }


}
