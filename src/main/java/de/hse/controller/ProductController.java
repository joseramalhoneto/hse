package de.hse.controller;

import de.hse.model.Product;
import de.hse.model.ProductCategory;
import de.hse.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product Product){
        Product ProductReturn = productService.saveProduct(Product);
        return new ResponseEntity<>(ProductReturn, HttpStatus.CREATED);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Product>> findAllProduct(){
        List<Product> allProducts = productService.findAllProduct();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") Long id){
        Product ProductById = productService.findProductById(id);
        return new ResponseEntity<>(ProductById, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product Product){
        Product ProductReturn = productService.updateProduct(Product);
        return new ResponseEntity<>(ProductReturn, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id){
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/allProductsByCategory")
    public ResponseEntity<List<ProductCategory>> findAllProductByCategory(){
        List<ProductCategory> allProducts = productService.getProductByCategory();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

}
