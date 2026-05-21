package com.flareye.productservice.repositories;

import com.flareye.productservice.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Optional<ProductCategory> findBySlug(String slug);
    Optional<ProductCategory> findByName(String name);
}
