package com.aashish.chat.WeChat.Jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import com.aashish.chat.WeChat.Service.OnlineUserService;

@Component
public class JwtInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private OnlineUserService onlineUserService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(StompCommand.CONNECT.equals(accessor.getCommand())){
        String token = accessor.getFirstNativeHeader("Authorization");
        if(token!=null && token.startsWith("Bearer ")){
            token = token.substring(7);
            try{
              Claims claims = jwtUtil.validaateToken(token);
              String userId = claims.getSubject();
              accessor.setUser(new StompPrincipal(userId));

                onlineUserService.setOnline(userId);

                simpMessagingTemplate.convertAndSendToUser(userId, "/queue/online", "online");




              

            }
            catch(Exception e){
                throw new RuntimeException("Invalid token");
            }
        }
        else{
            throw new RuntimeException("Invalid token");
        }
    }
    else if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
        String userId = accessor.getUser().getName();
        onlineUserService.setOffline(userId);
        simpMessagingTemplate.convertAndSendToUser(userId, "/queue/online", "offline");
    }
       

        return message;
        



        
    }
    }