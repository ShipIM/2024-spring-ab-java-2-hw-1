package com.example.homework.service;

import com.example.homework.model.entity.jpa.Message;

import java.util.List;

public interface MessageService {

    List<Message> getMessages();

    Message getMessage(long id);

    Message createMessage(Message message);

}
