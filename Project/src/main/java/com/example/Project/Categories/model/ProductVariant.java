package com.example.Project.Categories.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariant {

    @Id
    @NotBlank(message = "Variant ID cannot be empty")
    @Column(nullable = false, unique = true)
    private String variantId;

    @NotBlank(message = "Variant name cannot be empty")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    @Column(nullable = false)
    private Double price;

    @NotBlank(message = "Variant description cannot be empty")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Stock quantity is required")
    @Positive(message = "Stock must be greater than zero")
    @Column(nullable = false)
    private Integer stockQuantity;

    @NotBlank(message = "Image URL cannot be empty")
    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    @NotNull(message = "Product is required")
    private Product product;
}
