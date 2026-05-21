package com.flareye.productservice.repositories;

import com.flareye.productservice.models.Product;
import com.flareye.productservice.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();
    List<Product> findByCategory(ProductCategory category);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryAndActiveTrue(ProductCategory category);
}
