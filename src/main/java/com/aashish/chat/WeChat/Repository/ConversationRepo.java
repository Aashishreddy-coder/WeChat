package com.aashish.chat.WeChat.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.aashish.chat.WeChat.Domain.Conversation;

public interface ConversationRepo extends MongoRepository<Conversation, String>{

    List<Conversation> findByUserIdsContainingOrderByLastMessageTimeDesc(String userId);
                       
    Conversation findByUserIds(List<String> userIds);

    Optional<Conversation> findById(String id);
    
    @Query("{'userIds':{$all:[?0,?1]}}")
    Optional<Conversation> findByUserIdsContainingBoth(String userId, String recipientId);

    









    
}
