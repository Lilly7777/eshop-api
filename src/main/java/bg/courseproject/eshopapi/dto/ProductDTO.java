package bg.courseproject.eshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@Data
@JsonRootName(value = "product")
public class ProductDTO {

    @JsonProperty(value = "product_id", required = true)
    private Long id;

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "price")
    private Double price;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "stock_quantity")
    private Integer stockQuantity;

    @JsonProperty(value = "category")
    private CategoryDTO category;
}
