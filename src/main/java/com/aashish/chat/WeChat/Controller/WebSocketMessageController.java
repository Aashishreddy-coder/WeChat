package com.aashish.chat.WeChat.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;

import com.aashish.chat.WeChat.DTO.DeleteMessageEvent;
import com.aashish.chat.WeChat.DTO.ErrorMessageDTO;
import com.aashish.chat.WeChat.DTO.SendMessageRequest;

import com.aashish.chat.WeChat.Service.MessageService;
import com.aashish.chat.WeChat.DTO.TypingDto;

@Controller
public class WebSocketMessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload SendMessageRequest request,Principal principal){
        messageService.sendMessage(request,principal.getName());
    }

    @MessageMapping("/chat.typing")
    public  void chatTyping(@Payload TypingDto typingDto,Principal principal){
        messageService.chatTyping(typingDto,principal.getName());
    }

    @MessageMapping("/chat.deleteMessage")
    public void deleteMessage(@Payload  DeleteMessageEvent deleteMessageEvent,Principal principal){
        messageService.deleteMessage(deleteMessageEvent,principal.getName());
    }

    @MessageExceptionHandler(RuntimeException.class)
public void handleWsException(RuntimeException e, Principal principal) {
    simpMessagingTemplate.convertAndSendToUser(
        principal.getName(),
        "/queue/errors",
        new ErrorMessageDTO("ERROR", e.getMessage())
    );
}





   



    
    
}
