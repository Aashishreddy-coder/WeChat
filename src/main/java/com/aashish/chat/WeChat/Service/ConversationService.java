package com.aashish.chat.WeChat.Service;

import java.util.List;

import com.aashish.chat.WeChat.DTO.RecentChatDTO;

public interface ConversationService {
    public List<RecentChatDTO> getRecentChats();
    
}




