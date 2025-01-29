package bg.courseproject.eshopapi.repository;

import bg.courseproject.eshopapi.entity.Product;
import bg.courseproject.eshopapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
