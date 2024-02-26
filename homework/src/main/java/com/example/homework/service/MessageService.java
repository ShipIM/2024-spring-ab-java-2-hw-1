package com.example.homework.service;

import com.example.homework.model.entity.jpa.Image;
import com.example.homework.model.entity.jpa.Message;
import com.example.homework.repository.jpa.ImageRepository;
import com.example.homework.repository.jpa.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.homework.model.entity.mongo.Operation;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final OperationService operationService;

    private final MessageRepository messageRepository;
    private final ImageRepository imageRepository;

    public List<Message> loggedGetMessages() {
        operationService.logOperation(
                Operation.builder()
                        .message("Read all messages")
                        .time(LocalDateTime.now())
                        .type(OperationType.READ)
                        .build()
        );

        return getMessages();
    }

    @Cacheable(value = "MessageService::getMessages")
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public Message loggedGetMessage(long id) {
        Message message = getMessage(id);

        operationService.logOperation(
                Operation.builder()
                        .message(String.format("Read message: %s", message.getText()))
                        .time(LocalDateTime.now())
                        .type(OperationType.READ)
                        .build()
        );

        return message;
    }

    @Cacheable(value = "MessageService::getMessage", key = "#id")
    public Message getMessage(long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no message with such an id"));
    }

    @CacheEvict(value = "MessageService::getMessages", allEntries = true)
    public Message createMessage(Message message) {
        List<Image> images = message.getImages();

        if (!Objects.isNull(images)) {
            images = imageRepository.findAllByReferenceIn(images.stream()
                    .map(Image::getReference)
                    .collect(Collectors.toList()));

            if (message.getImages().size() != images.size()) {
                throw new EntityNotFoundException("Some images were not found");
            }

            message.setImages(images);
        }

        operationService.logOperation(
                Operation.builder()
                        .message(String.format("Saved message: %s", message.getText()))
                        .time(LocalDateTime.now())
                        .type(OperationType.WRITE)
                        .build()
        );

        return messageRepository.save(message);
    }

}
