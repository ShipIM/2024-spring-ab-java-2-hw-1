package com.example.homework.controller;

import com.example.homework.dto.message.CreateMessage;
import com.example.homework.dto.message.ResponseMessage;
import com.example.homework.dto.mapper.MessageMapper;
import com.example.homework.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper mapper;

    @QueryMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ResponseMessage> getMessages() {
        return mapper.toResponseList(messageService.getMessages());
    }

    @QueryMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage getMessage(@Argument long id) {
        return mapper.toResponse(messageService.getMessage(id));
    }

    @MutationMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseMessage createMessage(@Argument CreateMessage message) {
        return mapper.toResponse(messageService.createMessage(mapper.toMessage(message)));
    }

}
