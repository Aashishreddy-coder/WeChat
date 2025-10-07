package com.aashish.chat.WeChat.Exceptions;

public class UserEmailAlreadyExistException extends RuntimeException {

    public UserEmailAlreadyExistException(String message){
        super(message);
    }
    
    
}
