package com.aashish.chat.WeChat.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aashish.chat.WeChat.DTO.RecentChatDTO;
import com.aashish.chat.WeChat.Service.ConversationService;

@RestController 
@RequestMapping("/api/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping("/recent-chats")
    public ResponseEntity<List<RecentChatDTO>> getRecentChats() {
        return new ResponseEntity<>(conversationService.getRecentChats(), HttpStatus.OK);
    }

    




    
}
