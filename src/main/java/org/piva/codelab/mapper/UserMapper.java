package org.piva.codelab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.piva.codelab.dto.SignUpDto;
import org.piva.codelab.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    
    User mapToUser(SignUpDto signUpDto, String hashPassword);
}
