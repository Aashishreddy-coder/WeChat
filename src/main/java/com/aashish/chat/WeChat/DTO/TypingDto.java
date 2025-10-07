package com.aashish.chat.WeChat.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TypingDto {
    private String conversationId;
    private String senderId;
    
    private boolean isTyping;

    
}
