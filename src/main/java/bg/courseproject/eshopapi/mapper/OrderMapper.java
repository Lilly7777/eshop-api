package bg.courseproject.eshopapi.mapper;

import bg.courseproject.eshopapi.dto.OrderDTO;
import bg.courseproject.eshopapi.entity.Order;
import bg.courseproject.eshopapi.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    OrderDTO toDTO(Order order);
}
