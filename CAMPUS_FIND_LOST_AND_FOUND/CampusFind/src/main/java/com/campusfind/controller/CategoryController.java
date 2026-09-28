package com.campusfind.controller;

import com.campusfind.entity.Category;
import com.campusfind.exception.BadRequestException;
import com.campusfind.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {
    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Category> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Category create(@Valid @RequestBody Category category) {
        if (repository.findByNameIgnoreCase(category.getName()).isPresent()) {
            throw new BadRequestException("Category already exists");
        }
        return repository.save(category);
    }
}
