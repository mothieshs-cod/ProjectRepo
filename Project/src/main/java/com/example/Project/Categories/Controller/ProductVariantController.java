package com.example.Project.Categories.Controller;

import com.example.Project.Categories.model.ProductVariant;
import com.example.Project.Categories.service.FileStorageService;
import com.example.Project.Categories.service.ProductVariantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/variants")
public class ProductVariantController {

    private final ProductVariantService service;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper;

    public ProductVariantController(ProductVariantService service,
                                    FileStorageService fileStorageService,
                                    ObjectMapper objectMapper) {
        this.service = service;
        this.fileStorageService = fileStorageService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/{productId}", consumes = "multipart/form-data")
    public ProductVariant create(
            @PathVariable String productId,
            @RequestParam("variant") String variantJson,
            @RequestParam("image") MultipartFile image) throws Exception {

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Variant image is required");
        }

        ProductVariant variant = objectMapper.readValue(variantJson, ProductVariant.class);

        String fileName = fileStorageService.storeFile(image);
        variant.setImageUrl(fileName);

        return service.create(productId, variant);
    }

    @GetMapping("/product/{productId}")
    public List<ProductVariant> getByProduct(@PathVariable String productId) {
        return service.getByProduct(productId);
    }

    @PutMapping(value = "/{variantId}", consumes = "multipart/form-data")
    public ProductVariant update(
            @PathVariable String variantId,
            @RequestParam("variant") String variantJson,
            @RequestParam(value = "image", required = false) MultipartFile image) throws Exception {

        ProductVariant updated = objectMapper.readValue(variantJson, ProductVariant.class);
        ProductVariant existing = service.getById(variantId);

        if (image != null && !image.isEmpty()) {
            fileStorageService.delete(existing.getImageUrl());
            String fileName = fileStorageService.storeFile(image);
            updated.setImageUrl(fileName);
        } else {
            updated.setImageUrl(existing.getImageUrl());
        }

        return service.update(variantId, updated);
    }

    @DeleteMapping("/{variantId}")
    public ResponseEntity<?>  delete(@PathVariable String variantId) {
        boolean deleted = service.delete(variantId);

        if (!deleted) {
            return ResponseEntity
                    .status(404)
                    .body("Product Variant not found with id: " + variantId);
        }

        return ResponseEntity.accepted().body("Product Variant deleted");
    }
}