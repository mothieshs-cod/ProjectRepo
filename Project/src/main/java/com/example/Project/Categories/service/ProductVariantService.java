package com.example.Project.Categories.service;

import com.example.Project.Categories.model.Product;
import com.example.Project.Categories.model.ProductVariant;
import com.example.Project.Categories.repository.ProductRepository;
import com.example.Project.Categories.repository.ProductVariantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductVariantService {

    private final ProductVariantRepository variantRepository;
    private final ProductRepository productRepository;

    public ProductVariantService(ProductVariantRepository variantRepository,
                                 ProductRepository productRepository) {
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
    }

    public ProductVariant create(String productId, ProductVariant variant) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        variant.setProduct(product);
        return variantRepository.save(variant);
    }

    public List<ProductVariant> getByProduct(String productId) {
        return variantRepository.findByProduct_ProductId(productId);
    }

    public ProductVariant update(String variantId, ProductVariant updated) {
        ProductVariant existing = getById(variantId);
        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());
        existing.setStockQuantity(updated.getStockQuantity());
        existing.setDescription(updated.getDescription());
        existing.setImageUrl(updated.getImageUrl());

        return variantRepository.save(existing);
    }

    public ProductVariant getById(String variantId) {
        return variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));
    }

    public boolean delete(String variantId) {
        if (!variantRepository.existsById(variantId)) {
            return false;
        }

        variantRepository.deleteById(variantId);
        return true;
    }
}