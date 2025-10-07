package com.aashish.chat.WeChat.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest {
    private String conversationId;  
    private String recipientId;     
    private String content;
}   
