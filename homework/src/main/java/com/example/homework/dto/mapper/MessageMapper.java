package com.example.homework.dto.mapper;

import com.example.homework.dto.message.CreateMessage;
import com.example.homework.dto.message.ResponseMessage;
import com.example.homework.model.entity.jpa.Message;
import com.example.homework.model.entity.jpa.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface MessageMapper {

    @Mapping(target = "author", source = "user")
    Message toMessage(CreateMessage dto, User user);

    @Mapping(target = "author", source = "author.username")
    ResponseMessage toResponse(Message message);

    List<ResponseMessage> toResponseList(List<Message> messages);

}
