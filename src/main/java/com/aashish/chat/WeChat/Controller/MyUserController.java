package com.aashish.chat.WeChat.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aashish.chat.WeChat.DTO.LoginRequest;
import com.aashish.chat.WeChat.Domain.MyUser;
import com.aashish.chat.WeChat.Service.MyUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/users")
public class MyUserController {

    @Autowired
    private MyUserService myUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestPart("user") String user, @RequestPart("file") MultipartFile file) throws IOException{

        ObjectMapper objectMapper=new ObjectMapper();
        MyUser userObj=objectMapper.readValue(user, MyUser.class);

        MyUser registeredUser=myUserService.registerUser(userObj, file);

        return new ResponseEntity<>("Successfully registered user", HttpStatus.CREATED);

        

        
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest user){
        String token=myUserService.loginUser(user);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    

    
    
}
