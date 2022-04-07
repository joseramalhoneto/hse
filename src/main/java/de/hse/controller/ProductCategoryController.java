package de.hse.controller;

import de.hse.model.Product;
import de.hse.model.ProductCategory;
import de.hse.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<ProductCategory> saveProductCategory(@RequestBody ProductCategory productCategory){
        ProductCategory ProductReturn = productCategoryService.saveProductCategory(productCategory);
        return new ResponseEntity<>(ProductReturn, HttpStatus.CREATED);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<ProductCategory>> findProductByCategory(){
        List<ProductCategory> allProducts = productCategoryService.findProductByCategory();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductCategoryById(@PathVariable("id") Long id){
        productCategoryService.deleteProductCategoryById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
