package com.aashish.chat.WeChat.Service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.aashish.chat.WeChat.DTO.LoginRequest;
import com.aashish.chat.WeChat.Domain.MyUser;

public interface MyUserService {

    MyUser registerUser(MyUser user, MultipartFile file) throws IOException;

    String loginUser(LoginRequest user);



}
