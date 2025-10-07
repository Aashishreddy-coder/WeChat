package com.aashish.chat.WeChat.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteMessageEvent {
    String messageId;
    String conversationId;
    
}
