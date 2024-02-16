package com.example.homework.dto.mapper;

import com.example.homework.dto.CreateMessage;
import com.example.homework.dto.ResponseMessage;
import com.example.homework.entity.Message;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message toMessage(CreateMessage dto);

    ResponseMessage toResponse(Message message);

    List<ResponseMessage> toResponseList(List<Message> messages);

}
