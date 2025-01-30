package bg.courseproject.eshopapi.repository;

import bg.courseproject.eshopapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.category_id = :categoryId")
    Page<Product> findAllProductsByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    Page<Product> findAllProductsByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findAllProductsByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.description LIKE %:description%")
    Page<Product> findAllProductsByDescription(@Param("description") String description, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.quantityInStock = :stockQuantity")
    Page<Product> findAllProductsByStockQuantity(@Param("stockQuantity") Integer stockQuantity, Pageable pageable);

}
