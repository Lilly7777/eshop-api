package bg.courseproject.eshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@Data
@JsonRootName(value = "order")
public class OrderDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("order_date")
    private String orderDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_id")
    private Long paymentId;

    @JsonProperty("products")
    private String products;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

}
