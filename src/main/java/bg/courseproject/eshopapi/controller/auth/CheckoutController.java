package bg.courseproject.eshopapi.controller.auth;

import bg.courseproject.eshopapi.dto.*;
import bg.courseproject.eshopapi.service.OrderCreationService;
import bg.courseproject.eshopapi.service.PaymentProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api/")
@Tag(name = "Checkout Shopping Cart", description = "Endpoints for shopping cart checkout & order creation")
public class CheckoutController {

    private OrderCreationService orderCreationService;
    private PaymentProcessingService paymentProcessingService;


    @PostMapping("/checkout")
    @Operation(summary = "Checkout shopping cart", description = "Creates an order from the shopping cart")
    public ResponseEntity<OrderDTO> checkout(@RequestBody ShoppingCartDTO shoppingCartDTO) {

        // Verify item stock
        Set<InvoiceItemDTO> itemsInStock = shoppingCartDTO.getProducts()
                .stream()
                .peek(invoiceItem -> {
                    if (!orderCreationService.hasEnoughStock(invoiceItem.getProduct().getId(), invoiceItem.getQuantity())) {
                        throw new RuntimeException("Not enough stock for product with id: " + invoiceItem.getProduct().getId());
                    }
                }).collect(Collectors.toSet());

        OrderDTO orderDTO = orderCreationService.createOrder(shoppingCartDTO.getUserId(), itemsInStock);

        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping("/pay")
    @Operation(summary = "Pay for order", description = "Pay for an order")
    public ResponseEntity<InvoiceDTO> pay(@RequestBody PaymentDTO paymentDTO) {

        InvoiceDTO invoiceDTO = paymentProcessingService.processPayment(paymentDTO);

        return ResponseEntity.ok(invoiceDTO);
    }

}
