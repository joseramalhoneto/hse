package de.hse.service;

import de.hse.model.ProductCategory;
import de.hse.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public List<ProductCategory> findAllProductCategory() {
        return productCategoryRepository.findAll();
    }

    public void deleteProductCategoryById(Long id) {
        productCategoryRepository.deleteById(id);
    }
}
