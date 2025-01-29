package bg.courseproject.eshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.util.Set;


@Data
@JsonRootName(value = "shopping_cart")
public class ShoppingCartDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("products")
    private Set<ProductDTO> products;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
}
