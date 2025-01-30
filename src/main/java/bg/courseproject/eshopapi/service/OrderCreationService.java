package bg.courseproject.eshopapi.service;

import bg.courseproject.eshopapi.constant.OrderStatus;
import bg.courseproject.eshopapi.dto.InvoiceItemDTO;
import bg.courseproject.eshopapi.dto.OrderDTO;
import bg.courseproject.eshopapi.entity.Order;
import bg.courseproject.eshopapi.entity.Product;
import bg.courseproject.eshopapi.mapper.OrderMapper;
import bg.courseproject.eshopapi.repository.OrderRepository;
import bg.courseproject.eshopapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderCreationService {

    private ProductService productService;
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    private OrderMapper orderMapper;

    public OrderCreationService(ProductService productService, UserRepository userRepository, OrderRepository orderRepository, OrderMapper orderMapper) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }


    public boolean hasEnoughStock(Long productId, Integer quantity) {
        return productService.hasEnoughStock(productId, quantity);
    }

    public OrderDTO createOrder(Long userId, Set<InvoiceItemDTO> itemsInStock) {
        // Create order
        Order order = new Order();
        order.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
        Set<Product> products = itemsInStock.stream().map(invoiceItemDTO -> productService.getProductById(invoiceItemDTO.getProduct().getId()))
                .collect(Collectors.toSet());

        // Reduce stock quantity
        products.forEach(product -> {
            InvoiceItemDTO invoiceItemDTO = itemsInStock.stream()
                    .filter(invoiceItem -> invoiceItem.getProduct().getId().equals(product.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found in shopping cart"));
            productService.updateProductQuantity(product.getId(), product.getQuantityInStock() - invoiceItemDTO.getQuantity());
        });

        order.setProducts(products);
        order.setTotalPrice(itemsInStock.stream().mapToDouble(invoiceItemDTO -> invoiceItemDTO.getProduct().getPrice() * invoiceItemDTO.getQuantity()).sum());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")).getAddress());
        order = orderRepository.save(order);

        return orderMapper.toDTO(order);
    }
}
