package com.aashish.chat.WeChat.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.aashish.chat.WeChat.DTO.DeleteMessageEvent;
import com.aashish.chat.WeChat.DTO.SendMessageRequest;
import com.aashish.chat.WeChat.DTO.TypingDto;
import com.aashish.chat.WeChat.Domain.Conversation;
import com.aashish.chat.WeChat.Domain.Message;
import com.aashish.chat.WeChat.Repository.ConversationRepo;
import com.aashish.chat.WeChat.Repository.MessageRepo;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private ConversationRepo conversationRepo;

    public String userId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @Override
    public Message sendMessage(SendMessageRequest request,String userId) {

        Conversation conversation;

        if(request.getConversationId()!=null){
            conversation=conversationRepo.findById(request.getConversationId()).orElseThrow(() -> new RuntimeException("Conversation not found"));

        }
        else {
            conversation = conversationRepo
                    .findByUserIdsContainingBoth(userId, request.getRecipientId())
                    .orElseGet(() -> {
                        Conversation newConv = new Conversation();
                        newConv.setUserIds(Arrays.asList(userId, request.getRecipientId()));
                        newConv.setLastMessageTime(LocalDateTime.now());
                        return conversationRepo.save(newConv);
                    });



                }

                Message message = new Message();
            message.setSenderId(userId);
        message.setReceiverId(getRecipientId(conversation, userId, request));
        message.setContent(request.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setConversationId(conversation.getId());

        messageRepo.save(message);

        // Update conversation metadata
        conversation.setLastMessage(request.getContent());
        conversation.setLastMessageTime(LocalDateTime.now());
        conversationRepo.save(conversation);

        // TODO: push WebSocket event here
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverId(), "/queue/messages", message);
        simpMessagingTemplate.convertAndSendToUser(message.getSenderId(), "/queue/messages", message);

        





        return message;
    }

    private String getRecipientId(Conversation conversation, String senderId, SendMessageRequest request) {
        if (request.getRecipientId() != null) {
            return request.getRecipientId(); // new chat
        }
        return conversation.getUserIds()
                .stream()
                .filter(id -> !id.equals(senderId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Recipient not found"));
    }

    @Override
    public Page<Message> getMessages(String conversationId,int page,int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<Message> messages=messageRepo.findByConversationId(conversationId,pageable);
        if(messages.isEmpty()){
            return Page.empty();
        }
        return messages;
    }


    @Override
    public void chatTyping(TypingDto typingDto, String userId) {
        Conversation conversation = conversationRepo.findById(typingDto.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));


                String recipientId=conversation.getUserIds().stream().filter(id->!id.equals(userId)).findFirst().orElseThrow(() -> new RuntimeException("Recipient not found"));


                typingDto.setSenderId(userId);

                simpMessagingTemplate.convertAndSendToUser(recipientId, "/queue/typing", typingDto);

  

     
    }


    @Override
    public void deleteMessage(DeleteMessageEvent deleteMessageEvent, String userId) {

        Message message=messageRepo.findById(deleteMessageEvent.getMessageId())
        .orElseThrow(() -> new RuntimeException("Message not found"));

        if(!message.getSenderId().equals(userId)){
            throw new RuntimeException("You are not allowed to delete this message");
        }
        
        messageRepo.delete(message);

        Conversation conversation=conversationRepo.findById(deleteMessageEvent.getConversationId())
        .orElseThrow(() -> new RuntimeException("Conversation not found"));


                Message lastMessage=messageRepo.findFirstByConversationIdOrderByTimestampDesc(conversation.getId()).orElse(null);

        if(lastMessage!=null){
            conversation.setLastMessage(lastMessage.getContent());
            conversation.setLastMessageTime(lastMessage.getTimestamp());
          
        }
        else{
            conversation.setLastMessage(null);  
            conversation.setLastMessageTime(null);
           
        }

        conversationRepo.save(conversation);

        deleteMessageEvent.setConversationId(conversation.getId());

        for(String id:conversation.getUserIds()){

            simpMessagingTemplate.convertAndSendToUser(id, "/queue/deleteMessage", deleteMessageEvent);

        }

        






       
    }


}
