package fr.plb.springsecuritydemo.service.mapper;

import fr.plb.springsecuritydemo.domain.User;
import fr.plb.springsecuritydemo.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {

    User toEntity(UserDTO dto);

    UserDTO toDto(User entity);
}
