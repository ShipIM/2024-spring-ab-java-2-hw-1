package com.example.homework.service;

import com.example.homework.entity.Message;
import com.example.homework.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository repository;

    public List<Message> getMessages() {
        return repository.findAll();
    }

    public Message getMessage(long id) {
        return repository.getReferenceById(id);
    }

    public void addMessage(Message message) {
        repository.save(message);
    }
}
