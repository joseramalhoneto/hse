package de.hse.controller;

import de.hse.model.Category;
import de.hse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<Category> saveCategory(@RequestBody Category Category){
        Category CategoryReturn = categoryService.saveCategory(Category);
        return new ResponseEntity<>(CategoryReturn, HttpStatus.CREATED);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Category>> findAllCategory(){
        List<Category> allCategories = categoryService.findAllCategory();
        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable("id") Long id){
        Category CategoryById = categoryService.findCategoryById(id);
        return new ResponseEntity<>(CategoryById, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestBody Category Category){
        Category CategoryReturn = categoryService.updateCategory(Category);
        return new ResponseEntity<>(CategoryReturn, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Long id){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
