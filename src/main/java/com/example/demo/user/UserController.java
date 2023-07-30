package com.example.demo.user;

import com.example.demo.ortak.messages.MessageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {


    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping
    public UserViewModel getLoggedInUser(){
        return userService.getLoggedInUser();
    }
}
