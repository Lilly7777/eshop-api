package bg.courseproject.eshopapi.mapper;

import bg.courseproject.eshopapi.dto.ProductDTO;
import bg.courseproject.eshopapi.dto.UserDTO;
import bg.courseproject.eshopapi.entity.Product;
import bg.courseproject.eshopapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    Product toEntity(ProductDTO productDTO);

    ProductDTO toDTO(Product product);
}
