package com.example.homework.service;

import com.example.homework.entity.Message;
import com.example.homework.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no message with such an id"));
    }

    public Message createMessage(Message message) {
        return repository.save(message);
    }
}
