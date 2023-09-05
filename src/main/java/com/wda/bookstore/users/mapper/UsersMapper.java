package com.wda.bookstore.users.mapper;

import com.wda.bookstore.users.dto.UsersDTO;
import com.wda.bookstore.users.entity.UsersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    UsersEntity toModel(UsersDTO usersDTO);

    UsersDTO toDTO(UsersEntity usersEntity);
}
