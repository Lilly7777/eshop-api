package bg.courseproject.eshopapi.repository;

import bg.courseproject.eshopapi.constant.PaymentMethod;
import bg.courseproject.eshopapi.entity.Invoice;
import bg.courseproject.eshopapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    List<Invoice> findAllByUser(User user);

    List<Invoice> findAllByPayment(PaymentMethod paymentMethod);
}
