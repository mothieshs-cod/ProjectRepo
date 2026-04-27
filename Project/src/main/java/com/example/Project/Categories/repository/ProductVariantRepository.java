package com.example.Project.Categories.repository;

import com.example.Project.Categories.model.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProductVariantRepository extends JpaRepository<ProductVariant,String> {
    List<ProductVariant> findByProduct_ProductId(String productId);

}
