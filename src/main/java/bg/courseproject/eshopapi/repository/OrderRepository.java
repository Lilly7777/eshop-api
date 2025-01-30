package bg.courseproject.eshopapi.repository;

import bg.courseproject.eshopapi.constant.OrderStatus;
import bg.courseproject.eshopapi.entity.Order;
import bg.courseproject.eshopapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    List<Order> findAllByStatus(OrderStatus status);

    List<Order> findAllByOrderDateBetween(Instant startDate, Instant endDate);
}
