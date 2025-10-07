package com.aashish.chat.WeChat.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class MyUser {
    @Id
    private String id;
     private String username;
     private String email;
     private String avatarUrl;
    private String password; 

    
    


    
}
