package bg.courseproject.eshopapi.dto;

import bg.courseproject.eshopapi.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@Data
@JsonRootName(value = "product")
public class ProductDTO {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "price")
    private Double price;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "stock_quantity")
    private Integer stockQuantity;

    @JsonProperty(value = "category")
    private Category category;
}
