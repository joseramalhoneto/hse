package de.hse.service;

import de.hse.exception.ProductException;
import de.hse.exception.ResourceNotFoundException;
import de.hse.model.Product;
import de.hse.model.ProductCategory;
import de.hse.repository.ProductCategoryRepository;
import de.hse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    public Product saveProduct(Product product){
        Product productByName = productRepository.findProductByName(product.getName());

        if(productByName == null){
            return productRepository.save(product);
        }else{
            throw new ProductException("The product already exists. Try another name.");
        }
    }

    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }

    public Product findProductById(Long id){
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product updateProduct(Product Product){
        return productRepository.save(Product);
    }

    public void deleteProductById(Long id){
        productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        productRepository.deleteById(id);
    }

    public List<ProductCategory> getProductByCategory() {
        return productCategoryRepository.findAll();
    }
}
