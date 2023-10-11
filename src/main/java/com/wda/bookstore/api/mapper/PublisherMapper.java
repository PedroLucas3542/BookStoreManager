package com.wda.bookstore.api.mapper;

import com.wda.bookstore.api.dto.publisher.PublisherDTO;
import com.wda.bookstore.api.entity.PublisherEntity;

public class PublisherMapper {
    public PublisherEntity mapDtoToEntity(PublisherDTO dto) {
        if (dto == null) {
            return null;
        }

        PublisherEntity entity = new PublisherEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCity(dto.getCity());

        return entity;
    }

    public PublisherDTO mapEntityToDto(PublisherEntity entity) {
        if (entity == null) {
            return null;
        }

        PublisherDTO dto = new PublisherDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        entity.setCity(dto.getCity());

        return dto;
    }

    public Long mapDtoIdToEntityId(long dtoId) {
        return dtoId;
    }

    public Long mapEntityIdToDtoId(long entityId) {
        return entityId;
    }
}
