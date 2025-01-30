package bg.courseproject.eshopapi.controller;

import bg.courseproject.eshopapi.dto.*;
import bg.courseproject.eshopapi.service.OrderCreationService;
import bg.courseproject.eshopapi.service.PaymentProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CheckoutControllerTest {

    @Mock
    private OrderCreationService orderCreationService;

    @Mock
    private PaymentProcessingService paymentProcessingService;

    @InjectMocks
    private CheckoutController checkoutController;

    private MockMvc mockMvc;
    private ShoppingCartDTO shoppingCartDTO;
    private PaymentDTO paymentDTO;
    private OrderDTO orderDTO;
    private InvoiceDTO invoiceDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(checkoutController).build();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setPrice(100.0);

        InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO();
        invoiceItemDTO.setProduct(productDTO);
        invoiceItemDTO.setQuantity(1);

        shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setUserId(1L);
        shoppingCartDTO.setProducts(Set.of(invoiceItemDTO));

        paymentDTO = new PaymentDTO();
        paymentDTO.setOrderId(1L);
        paymentDTO.setAmount(100.0);

        orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setTotalPrice(100.0);

        invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceNumber("INV-001");
        invoiceDTO.setTotalAmount(100.0);
    }

    @Test
    void testCheckout() throws Exception {
        when(orderCreationService.hasEnoughStock(1L, 1)).thenReturn(true);
        when(orderCreationService.createOrder(1L, shoppingCartDTO.getProducts())).thenReturn(orderDTO);

        mockMvc.perform(post("/v1/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\": 1, \"products\": [{\"product\": {\"id\": 1, \"name\": \"Test Product\", \"price\": 100.0}, \"quantity\": 1}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(100.0));
    }

    @Test
    void testPay() throws Exception {
        when(paymentProcessingService.processPayment(paymentDTO)).thenReturn(invoiceDTO);

        mockMvc.perform(post("/v1/api/pay")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\": 1, \"amount\": 100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceNumber").value("INV-001"));
    }
}
