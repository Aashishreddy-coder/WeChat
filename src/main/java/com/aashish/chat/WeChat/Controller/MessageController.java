package com.aashish.chat.WeChat.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import com.aashish.chat.WeChat.DTO.MessagesResponse;
import com.aashish.chat.WeChat.DTO.SendMessageRequest;
import com.aashish.chat.WeChat.Domain.Message;
import com.aashish.chat.WeChat.Service.MessageService;


@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    private String userId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageRequest request) {

        Message message=messageService.sendMessage(request,userId());
        return new ResponseEntity<>(message, HttpStatus.CREATED);

    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<MessagesResponse> getMessages(@PathVariable String conversationId,@RequestParam int page,@RequestParam int size) {
        Page<Message> messages=messageService.getMessages(conversationId,page,size);
        MessagesResponse response = new MessagesResponse();
        response.setMessages(messages.getContent());
        response.setHasNext(messages.hasNext());
        response.setPage(page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
