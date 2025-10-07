package com.aashish.chat.WeChat.Exceptions;


public class NoRecentConversationException extends RuntimeException {

    public NoRecentConversationException(String message){
        super(message);
    }
    
}
