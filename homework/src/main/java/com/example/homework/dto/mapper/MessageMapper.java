package com.example.homework.dto.mapper;

import com.example.homework.dto.message.CreateMessage;
import com.example.homework.dto.message.ResponseMessage;
import com.example.homework.model.entity.jpa.Message;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ImageMapper.class })
public interface MessageMapper {

    Message toMessage(CreateMessage dto);

    ResponseMessage toResponse(Message message);

    List<ResponseMessage> toResponseList(List<Message> messages);

}
