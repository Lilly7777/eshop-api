package bg.courseproject.eshopapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;


@Data
@JsonRootName(value = "invoice")
public class InvoiceDTO {

    @JsonProperty(value = "invoice_number", required = true)
    private String invoiceNumber;

    @JsonProperty(value = "issue_date", required = true)
    private String issueDate;

    @JsonProperty(value = "total_amount", required = true)
    private Double totalAmount;

    @JsonProperty(value = "payment_id", required = true)
    private Long paymentId;

    @JsonProperty(value = "user_id", required = true)
    private Long userId;

    @JsonProperty(value = "items", required = true)
    private InvoiceItemDTO[] items;
}
