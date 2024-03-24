package com.example.homework.service.impl;

import com.example.homework.annotation.Audit;
import com.example.homework.model.entity.jpa.Image;
import com.example.homework.model.entity.jpa.Message;
import com.example.homework.model.entity.jpa.User;
import com.example.homework.model.entity.mongo.enumeration.OperationType;
import com.example.homework.repository.jpa.ImageRepository;
import com.example.homework.repository.jpa.MessageRepository;
import com.example.homework.service.DetailsService;
import com.example.homework.service.MessageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ImageRepository imageRepository;
    private final DetailsService detailsService;

    @Cacheable(value = "MessageService::getMessages")
    @Audit(message = "messages read", type = OperationType.READ)
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @Cacheable(value = "MessageService::getMessage", key = "#id")
    @Audit(message = "message read", type = OperationType.READ)
    public Message getMessage(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("there is no message with such an id"));
    }

    @CacheEvict(value = "MessageService::getMessages", allEntries = true)
    @Audit(message = "message saved", type = OperationType.WRITE)
    public Message createMessage(Message message) {
        List<Image> images = message.getImages();

        if (Objects.nonNull(images)) {
            images = imageRepository.findAllByReferenceIn(images.stream()
                    .map(Image::getReference)
                    .collect(Collectors.toList()));

            if (message.getImages().size() != images.size()) {
                throw new EntityNotFoundException("some images were not found");
            }

            message.setImages(images);
        }

        User user = (User) detailsService.loadUserByUsername(message.getAuthor().getUsername());
        message.setAuthor(user);

        return messageRepository.save(message);
    }

}
