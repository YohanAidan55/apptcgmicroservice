package com.aidan.userservice.user.repository.mapper;

import com.aidan.userservice.user.domain.dto.UserDTO;
import com.aidan.userservice.user.repository.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserDTO userDTO);

    UserDTO toDto(UserEntity userEntity);

    com.aidan.security.client.dto.UserDTO toDtoSecurity(UserEntity userEntity);

    List<UserDTO> toDto(List<UserEntity> userEntities);
}
