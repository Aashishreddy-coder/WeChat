package com.aashish.chat.WeChat.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.aashish.chat.WeChat.DTO.RecentChatDTO;
import com.aashish.chat.WeChat.Domain.Conversation;
import com.aashish.chat.WeChat.Domain.MyUser;
import com.aashish.chat.WeChat.Repository.ConversationRepo;
import com.aashish.chat.WeChat.Repository.MyUserRepo;

@Service
public class conversationServiceImpl implements ConversationService{

    @Autowired
    private ConversationRepo conversationRepo;

    public String userId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Autowired
    private MyUserRepo userRepo;

    @Override
    public List<RecentChatDTO> getRecentChats() {
        String userId=userId();
      
        List<Conversation> conversations=conversationRepo.findByUserIdsContainingOrderByLastMessageTimeDesc(userId);
        if(conversations.isEmpty()){
            return new ArrayList<>();
        }

        List<RecentChatDTO> recentChats=new ArrayList<>();

        for(Conversation conv:conversations){

            String otherUserId=conv.getUserIds().stream().filter(id->!id.equals(userId)).findFirst().orElse(null);

            if(otherUserId!=null){
                MyUser otherUser = userRepo.findById(otherUserId).orElseThrow();
                RecentChatDTO dto=new RecentChatDTO();
                dto.setUserId(otherUserId);
                dto.setUsername(otherUser.getUsername());
                dto.setAvatarUrl(otherUser.getAvatarUrl());
                dto.setLastMessage(conv.getLastMessage());
                dto.setLastMessageTime(conv.getLastMessageTime());
                recentChats.add(dto);



            }
        }
        return recentChats;

    }

    


    
}
