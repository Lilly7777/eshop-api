package bg.courseproject.eshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@Data
@JsonRootName(value = "payment")
public class PaymentDTO {

    @JsonProperty(value = "id", required = true)
    private Long id;

    @JsonProperty(value = "amount", required = true)
    private Double amount;

    @JsonProperty(value = "payment_date", required = true)
    private String paymentDate;

    @JsonProperty(value = "payment_method", required = true)
    private String paymentMethod;

    @JsonProperty(value = "payment_status", required = true)
    private String paymentStatus;

    @JsonProperty(value = "user_id", required = true)
    private Long userId;

    @JsonProperty(value = "order_id", required = true)
    private Long orderId;

    @JsonProperty(value = "created_at", required = true)
    private String createdAt;

    @JsonProperty(value = "updated_at", required = true)
    private String updatedAt;

}
