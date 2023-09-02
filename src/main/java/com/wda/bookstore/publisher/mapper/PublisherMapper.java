package com.wda.bookstore.publisher.mapper;

import com.wda.bookstore.publisher.dto.PublisherDTO;
import com.wda.bookstore.publisher.entity.PublisherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherDTO toDTO(PublisherEntity entity);
    PublisherEntity toModel(PublisherDTO publisherDTO);
}
