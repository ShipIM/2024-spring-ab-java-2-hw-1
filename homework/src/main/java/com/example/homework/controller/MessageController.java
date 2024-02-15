package com.example.homework.controller;

import com.example.homework.dto.MessageCreateDto;
import com.example.homework.dto.MessageResponseDto;
import com.example.homework.dto.mapper.MessageMapper;
import com.example.homework.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper mapper;

    @GetMapping
    public List<MessageResponseDto> getMessages() {
        return mapper.toResponseList(messageService.getMessages());
    }

    @GetMapping("/{id}")
    public MessageResponseDto getMessage(@PathVariable("id") long id) {
        return mapper.toResponse(messageService.getMessage(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addMessage(@Valid @RequestBody MessageCreateDto message) {
        messageService.addMessage(mapper.toMessage(message));
    }
}
