package bg.courseproject.eshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@Data
@JsonRootName(value = "invoice_item")
public class InvoiceItemDTO {

    @JsonProperty(value = "quantity")
    private Integer quantity;

    @JsonProperty(value = "price")
    private Double price;

    @JsonProperty(value = "total")
    private Double total;

    @JsonProperty(value = "product")
    private ProductDTO product;

    @JsonProperty(value = "invoice")
    private InvoiceDTO invoice;

}
