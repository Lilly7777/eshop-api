package bg.courseproject.eshopapi.mapper;

import bg.courseproject.eshopapi.dto.InvoiceDTO;
import bg.courseproject.eshopapi.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InvoiceMapper {

    InvoiceDTO toDTO(Invoice invoice);
}
