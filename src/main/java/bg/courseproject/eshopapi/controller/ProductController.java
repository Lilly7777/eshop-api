package bg.courseproject.eshopapi.controller;

import bg.courseproject.eshopapi.dto.ProductDTO;
import bg.courseproject.eshopapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Get all products with pagination")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(@RequestParam int page,
                                                           @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id", description = "Get product by id")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductDTOById(id));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category id", description = "Get products by category id with pagination")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategoryId(@PathVariable Integer categoryId, @RequestParam int page,
                                                                    @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByCategoryId(categoryId, pageable));
    }

    @GetMapping("/search-by-name")
    @Operation(summary = "Get products by name", description = "Get products by name with pagination")
    public ResponseEntity<Page<ProductDTO>> getProductsByName(@RequestParam String name, @RequestParam int page,
                                                              @RequestParam int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByName(name, pageable));
    }

    @PostMapping
    @Operation(summary = "Create product", description = "Create a new product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update product by id")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.updateProduct(id, productDTO));
    }

    @PatchMapping("/{id}/quantity")
    @Operation(summary = "Update product quantity", description = "Update product quantity by id")
    public ResponseEntity<Void> updateProductQuantity(@PathVariable Long id, @RequestParam Integer newQuantity) {
        productService.updateProductQuantity(id, newQuantity);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Delete product by id")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
