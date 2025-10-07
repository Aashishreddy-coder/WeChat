package com.aashish.chat.WeChat.Service;

import org.springframework.data.domain.Page;

import com.aashish.chat.WeChat.DTO.DeleteMessageEvent;
import com.aashish.chat.WeChat.DTO.SendMessageRequest;
import com.aashish.chat.WeChat.DTO.TypingDto;
import com.aashish.chat.WeChat.Domain.Message;

public interface MessageService {

    Message sendMessage(SendMessageRequest request,String userId);

    Page<Message> getMessages(String conversationId,int page,int size);

    void chatTyping(TypingDto typingDto,String userId);

    void deleteMessage(DeleteMessageEvent deleteMessageEvent, String userId);
    
}
