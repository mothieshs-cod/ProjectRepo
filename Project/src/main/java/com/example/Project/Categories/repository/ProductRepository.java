package com.example.Project.Categories.repository;

import com.example.Project.Categories.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProductRepository extends JpaRepository<Product,String> {
    List<Product> findByCategory_CategoryId(Long categoryId);
}
