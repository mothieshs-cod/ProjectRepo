package com.example.Project.Categories.Controller;

import com.example.Project.Categories.model.Category;
import com.example.Project.Categories.service.CategoryService;
import com.example.Project.Categories.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper;

    public CategoryController(CategoryService service,
                              FileStorageService fileStorageService,
                              ObjectMapper objectMapper) {
        this.service = service;
        this.fileStorageService = fileStorageService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = "multipart/form-data")
    public Category create(
            @RequestParam("category") String categoryJson,
            @RequestParam("image") MultipartFile image) throws Exception {

        Category category = objectMapper.readValue(categoryJson, Category.class);

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("Category image is required");
        }

        String imageName = fileStorageService.storeFile(image);
        category.setImageUrl(imageName);

        return service.create(category);
    }

    @GetMapping
    public List<Category> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public Category update(
            @PathVariable Long id,
            @RequestParam("category") String categoryJson,
            @RequestParam(value = "image", required = false) MultipartFile image) throws Exception {

        Category updated = objectMapper.readValue(categoryJson, Category.class);
        Category existing = service.getById(id);

        if (image != null && !image.isEmpty()) {
            fileStorageService.delete(existing.getImageUrl());
            String imageName = fileStorageService.storeFile(image);
            updated.setImageUrl(imageName);
        } else {
            updated.setImageUrl(existing.getImageUrl());
        }

        return service.update(id, updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.accepted().body("Category deleted");
    }
}