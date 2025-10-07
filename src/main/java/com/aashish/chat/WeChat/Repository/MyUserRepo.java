package com.aashish.chat.WeChat.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.aashish.chat.WeChat.Domain.MyUser;

public interface MyUserRepo extends MongoRepository<MyUser, String>{

    MyUser findByEmail(String email);
    MyUser findByEmailAndPassword(String email, String password);




}
