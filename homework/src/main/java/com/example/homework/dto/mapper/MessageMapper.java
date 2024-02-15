package com.example.homework.dto.mapper;

import com.example.homework.dto.MessageCreateDto;
import com.example.homework.dto.MessageResponseDto;
import com.example.homework.entity.Message;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message toMessage(MessageCreateDto dto);

    MessageResponseDto toResponse(Message message);

    List<MessageResponseDto> toResponseList(List<Message> messages);

}
