package de.hse.service;

import de.hse.exception.CategoryException;
import de.hse.exception.ResourceNotFoundException;
import de.hse.model.Category;
import de.hse.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category){
        Category byName = categoryRepository.findByName(category.getName());

        if(byName == null){
            return categoryRepository.save(category);
        }else{
            throw new CategoryException("The Category already exists. Try another name.");
        }
    }

    public List<Category> findAllCategory(){
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long id){
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public Category updateCategory(Category category){
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Long id){
        categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        categoryRepository.deleteById(id);
    }

}
