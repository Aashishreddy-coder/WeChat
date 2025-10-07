package com.aashish.chat.WeChat.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aashish.chat.WeChat.DTO.LoginRequest;
import com.aashish.chat.WeChat.Domain.MyUser;
import com.aashish.chat.WeChat.Exceptions.InvalidUserCredentialsException;
import com.aashish.chat.WeChat.Exceptions.UserEmailAlreadyExistException;
import com.aashish.chat.WeChat.Jwt.JwtUtil;
import com.aashish.chat.WeChat.Repository.MyUserRepo;



@Service
public class MyUserServiceImpl implements MyUserService {

    @Value("${project.image}")
    private String path;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserRepo myUserRepo;
    @Autowired
    private FileService fileService;

    @Override
    public MyUser registerUser(MyUser user, MultipartFile file) throws IOException {

        MyUser existingUser=myUserRepo.findByEmail(user.getEmail());
        if(existingUser!=null){
            throw new UserEmailAlreadyExistException("User already exists");
        }
        
        String fileName=fileService.uploadImage(path, file);
        user.setAvatarUrl(fileName);
        myUserRepo.save(user);

        return user;
       
        
    }

    @Override
    public String loginUser(LoginRequest user) {
        
        MyUser existingUser=myUserRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if(existingUser==null){
            throw new InvalidUserCredentialsException("Invalid credentials");
        }
        
        return jwtUtil.generateToken(existingUser);

        
    }

    
    
}
