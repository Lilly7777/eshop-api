package bg.courseproject.eshopapi.service;

import bg.courseproject.eshopapi.constant.OrderStatus;
import bg.courseproject.eshopapi.dto.InvoiceDTO;
import bg.courseproject.eshopapi.dto.PaymentDTO;
import bg.courseproject.eshopapi.entity.Invoice;
import bg.courseproject.eshopapi.entity.Order;
import bg.courseproject.eshopapi.entity.Payment;
import bg.courseproject.eshopapi.exception.BadRequestException;
import bg.courseproject.eshopapi.exception.NotFoundException;
import bg.courseproject.eshopapi.mapper.InvoiceMapper;
import bg.courseproject.eshopapi.mapper.PaymentMapper;
import bg.courseproject.eshopapi.repository.InvoiceRepository;
import bg.courseproject.eshopapi.repository.OrderRepository;
import bg.courseproject.eshopapi.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class PaymentProcessingService {

    private OrderRepository orderRepository;
    private PaymentRepository paymentRepository;
    private InvoiceRepository invoiceRepository;

    private PaymentMapper paymentMapper;
    private InvoiceMapper invoiceMapper;

    public PaymentProcessingService(OrderRepository orderRepository, PaymentRepository paymentRepository, InvoiceRepository invoiceRepository, PaymentMapper paymentMapper, InvoiceMapper invoiceMapper) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentMapper = paymentMapper;
        this.invoiceMapper = invoiceMapper;
    }

    public InvoiceDTO processPayment(PaymentDTO paymentDTO) {
        Order order = validateOrder(paymentDTO);
        Payment payment = savePayment(paymentDTO);
        updateOrderWithPayment(order, payment);
        Invoice invoice = createInvoice(order, payment, paymentDTO.getAmount());
        return invoiceMapper.toDTO(invoice);
    }

    private Order validateOrder(PaymentDTO paymentDTO) {
        Order order = orderRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (!Objects.equals(order.getTotalPrice(), paymentDTO.getAmount())) {
            throw new BadRequestException();
        }
        return order;
    }

    private Payment savePayment(PaymentDTO paymentDTO) {
        Payment payment = paymentMapper.fromDTO(paymentDTO);
        return paymentRepository.save(payment);
    }

    private void updateOrderWithPayment(Order order, Payment payment) {
        order.setPayment(payment);
        order.setStatus(OrderStatus.PROCESSING);
        orderRepository.save(order);
    }

    private Invoice createInvoice(Order order, Payment payment, Double amount) {
        Invoice invoice = new Invoice();
        invoice.setPayment(payment);
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setTotalAmount(amount);
        invoice.setUser(order.getUser());
        return invoiceRepository.save(invoice);
    }

    private String generateInvoiceNumber() {
        return "INV-" + UUID.randomUUID().toString();
    }
}
