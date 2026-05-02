package com.example.Project.Categories.Controller;


import com.example.Project.Categories.model.Product;
import com.example.Project.Categories.service.FileStorageService;
import com.example.Project.Categories.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper;

    public ProductController(ProductService service,
                             FileStorageService fileStorageService,
                             ObjectMapper objectMapper) {
        this.service = service;
        this.fileStorageService = fileStorageService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/{categoryId}", consumes = "multipart/form-data")
    public Product create(
            @PathVariable Long categoryId,
            @RequestParam("product") String productJson,
            @RequestParam("image") MultipartFile image) throws Exception {

        Product product = objectMapper.readValue(productJson, Product.class);

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Product image is required");
        }

        String fileName = fileStorageService.storeFile(image);
        product.setImageUrl(fileName);

        return service.create(categoryId, product);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getByCategory(@PathVariable Long categoryId) {
        return service.getByCategory(categoryId);
    }

    @GetMapping("/{productId}")
    public Product getById(@PathVariable String productId) {
        return service.getById(productId);
    }

    @GetMapping("/search")
    public List<Product> getAllProducts(){
        return service.getAllProducts();
    }

    @PutMapping(value = "/{productId}", consumes = "multipart/form-data")
    public Product update(
            @PathVariable String productId,
            @RequestParam("product") String productJson,
            @RequestParam(value = "image", required = false) MultipartFile image) throws Exception {

        Product updated = objectMapper.readValue(productJson, Product.class);
        Product existing = service.getById(productId);

        if (image != null && !image.isEmpty()) {
            fileStorageService.delete(existing.getImageUrl());
            String fileName = fileStorageService.storeFile(image);
            updated.setImageUrl(fileName);
        } else {
            updated.setImageUrl(existing.getImageUrl());
        }

        return service.update(productId, updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {

        boolean deleted = service.delete(id);

        if (!deleted) {
            return ResponseEntity
                    .status(404)
                    .body("Product not found with id: " + id);
        }

        return ResponseEntity.accepted().body("Product deleted");
    }
}