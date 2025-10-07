package com.aashish.chat.WeChat.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentChatDTO {
    private String userId;          // the other user's ID
    private String username;        // the other user's display name
    private String avatarUrl;       // profile picture
    private boolean online;         // online status (from Redis later)
    private String lastMessage;     // last message text
    private LocalDateTime lastMessageTime; // timestamp of last message
}