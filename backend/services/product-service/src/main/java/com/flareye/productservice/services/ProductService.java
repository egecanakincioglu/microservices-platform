package com.flareye.productservice.services;

import com.flareye.productservice.dto.CreateProductRequest;
import com.flareye.productservice.dto.ProductDTO;
import com.flareye.productservice.models.Product;
import com.flareye.productservice.models.ProductCategory;
import com.flareye.productservice.repositories.ProductCategoryRepository;
import com.flareye.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    public List<ProductDTO> findAllActive() {
        return productRepository.findByActiveTrue().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<ProductDTO> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findByCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(cat -> productRepository.findByCategoryAndActiveTrue(cat).stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public Product create(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setActive(true);

        if (request.getCategoryId() != null) {
            categoryRepository.findById(request.getCategoryId())
                    .ifPresent(product::setCategory);
        }

        return productRepository.save(product);
    }

    public void deactivate(Long id) {
        productRepository.findById(id).ifPresent(p -> {
            p.setActive(false);
            productRepository.save(p);
        });
    }

    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .imageUrl(product.getImageUrl())
                .active(product.getActive())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .createdAt(product.getCreatedAt())
                .build();
    }
}
