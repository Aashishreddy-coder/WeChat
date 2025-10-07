package com.aashish.chat.WeChat.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.aashish.chat.WeChat.Domain.Message;  

public interface MessageRepo extends MongoRepository<Message, String>{

    Page<Message>  findByConversationId(String conversationId,Pageable pageable);

    Optional<Message> findFirstByConversationIdOrderByTimestampDesc(String conversationId);

    











    
}
