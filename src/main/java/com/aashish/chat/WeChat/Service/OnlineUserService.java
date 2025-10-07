package com.aashish.chat.WeChat.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component

public class OnlineUserService {

    private final Map<String, Boolean> onlineUsers = new ConcurrentHashMap<>();

    public void setOnline(String userId){
        onlineUsers.put(userId, true);
    }
    
    public void setOffline(String userId){
        onlineUsers.put(userId, false);
    }

    public boolean isOnline(String userId){
        return onlineUsers.containsKey(userId);
    }

    




    
}
