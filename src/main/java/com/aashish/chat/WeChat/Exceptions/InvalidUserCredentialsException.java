package com.aashish.chat.WeChat.Exceptions;

public class InvalidUserCredentialsException extends RuntimeException { 


    public InvalidUserCredentialsException(String message){
        super(message);
    }
    
    
}