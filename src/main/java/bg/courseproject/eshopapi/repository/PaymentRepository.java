package bg.courseproject.eshopapi.repository;

import bg.courseproject.eshopapi.constant.PaymentMethod;
import bg.courseproject.eshopapi.constant.PaymentStatus;
import bg.courseproject.eshopapi.entity.Payment;
import bg.courseproject.eshopapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByUser(User user);

    List<Payment> findAllByPaymentMethod(PaymentMethod paymentMethod);

    List<Payment> findAllByPaymentStatus(PaymentStatus paymentStatus);

    List<Payment> findAllByPaymentDateBetween(Instant startDate, Instant endDate);
}
