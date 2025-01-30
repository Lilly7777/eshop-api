package bg.courseproject.eshopapi.mapper;

import bg.courseproject.eshopapi.dto.UserDTO;
import bg.courseproject.eshopapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User fromDTO(UserDTO user);

}
