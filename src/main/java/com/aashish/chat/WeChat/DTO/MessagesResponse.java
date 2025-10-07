package com.aashish.chat.WeChat.DTO;

import java.util.List;

import com.aashish.chat.WeChat.Domain.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagesResponse {
    private List<Message> messages;
    private boolean hasNext;
    private int page;
}
