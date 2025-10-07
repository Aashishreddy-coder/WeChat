package com.aashish.chat.WeChat.Domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Conversation {

    @Id
    private String id;
    private List<String> userIds;          
    private String lastMessage;            
    private LocalDateTime lastMessageTime; 
    private Map<String, Integer> unreadCounts;
    
    







    
}
