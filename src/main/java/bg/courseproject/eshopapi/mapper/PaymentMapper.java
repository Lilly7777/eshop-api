package bg.courseproject.eshopapi.mapper;

import bg.courseproject.eshopapi.dto.PaymentDTO;
import bg.courseproject.eshopapi.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    Payment fromDTO(PaymentDTO payment);
}
