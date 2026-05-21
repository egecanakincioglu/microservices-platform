package com.flareye.productservice.controllers;

import com.flareye.productservice.dto.CreateProductRequest;
import com.flareye.productservice.dto.ProductDTO;
import com.flareye.productservice.models.Product;
import com.flareye.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(productService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return product.map(p -> ResponseEntity.ok(productService.toDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> search(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.findByCategory(categoryId));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody CreateProductRequest request) {
        Product product = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.toDTO(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        productService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
