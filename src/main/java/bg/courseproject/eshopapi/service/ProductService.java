package bg.courseproject.eshopapi.service;

import bg.courseproject.eshopapi.dto.ProductDTO;
import bg.courseproject.eshopapi.entity.Product;
import bg.courseproject.eshopapi.exception.NotFoundException;
import bg.courseproject.eshopapi.mapper.ProductMapper;
import bg.courseproject.eshopapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public boolean hasEnoughStock(Long productId, Integer quantity) {
        return getProductDTOById(productId).getStockQuantity() >= quantity;
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDTO);
    }

    public ProductDTO getProductDTOById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        return productMapper.toDTO(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    public Page<ProductDTO> getProductsByCategoryId(Integer categoryId, Pageable pageable) {
        return productRepository.findAllProductsByCategoryId(categoryId, pageable)
                .map(productMapper::toDTO);
    }

    public Page<ProductDTO> getProductsByName(String name, Pageable pageable) {
        return productRepository.findAllProductsByName(name, pageable)
                .map(productMapper::toDTO);
    }

    public Page<ProductDTO> getProductsByPriceRange(Double minPrice, Double maxPrice, Pageable pageable) {
        return productRepository.findAllProductsByPriceRange(minPrice, maxPrice, pageable)
                .map(productMapper::toDTO);
    }

    public Page<ProductDTO> getProductsByDescription(String description, Pageable pageable) {
        return productRepository.findAllProductsByDescription(description, pageable)
                .map(productMapper::toDTO);
    }

    public Page<ProductDTO> getProductsByStockQuantity(Integer stockQuantity, Pageable pageable) {
        return productRepository.findAllProductsByStockQuantity(stockQuantity, pageable)
                .map(productMapper::toDTO);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantityInStock(productDTO.getStockQuantity());
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    public void updateProductQuantity(Long id, Integer newQuantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        product.setQuantityInStock(newQuantity);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
