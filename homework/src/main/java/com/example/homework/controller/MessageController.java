package com.example.homework.controller;

import com.example.homework.dto.mapper.MessageMapper;
import com.example.homework.dto.mapper.UserMapper;
import com.example.homework.dto.message.CreateMessage;
import com.example.homework.dto.message.ResponseMessage;
import com.example.homework.model.entity.jpa.Message;
import com.example.homework.model.entity.jpa.User;
import com.example.homework.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final UserMapper userMapper;

    @QueryMapping
    @PreAuthorize("hasAuthority('MESSAGE_READ_PRIVILEGE')")
    public List<ResponseMessage> getMessages() {
        return messageMapper.toResponseList(messageService.getMessages());
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('MESSAGE_READ_PRIVILEGE')")
    public ResponseMessage getMessage(@Argument Long id) {
        return messageMapper.toResponse(messageService.getMessage(id));
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('MESSAGE_WRITE_PRIVILEGE')")
    public ResponseMessage createMessage(@Argument CreateMessage message, Principal principal) {
        User user = userMapper.mapToUser(principal);
        Message entity = messageMapper.toMessage(message, user);

        return messageMapper.toResponse(messageService.createMessage(entity));
    }

}
