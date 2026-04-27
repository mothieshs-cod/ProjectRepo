package com.example.Project.Categories.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @NotBlank(message = "Product ID cannot be empty")
    @Column(nullable = false, unique = true)
    private String productId;

    @NotBlank(message = "Product name cannot be empty")
    @Column(nullable = false)
    private String name;

    private String description;

    @NotBlank(message = "Image URL cannot be empty")
    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Category is required")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductVariant> variants;
}