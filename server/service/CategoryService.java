
package com.stockmanagement.server.service;

import com.stockmanagement.server.entity.Category;
import com.stockmanagement.server.repository.CategoryRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Add category
    public Category addCategory(Category category) {
        category.setId(null);
        return categoryRepository.save(category);
    }

    // Update category
    public Category updateCategory(Long id, Category newCategory) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setName(newCategory.getName());
        existingCategory.setDescription(newCategory.getDescription());

        return categoryRepository.save(existingCategory);
    }

    // Delete category
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }

        categoryRepository.deleteById(id);
    }
}
