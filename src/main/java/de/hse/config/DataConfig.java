package de.hse.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hse.model.Category;
import de.hse.model.Product;
import de.hse.model.ProductCategory;
import de.hse.repository.CategoryRepository;
import de.hse.repository.ProductCategoryRepository;
import de.hse.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class DataConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public DataConfig(ProductRepository productRepository,
                      CategoryRepository categoryRepository,
                      ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = TypeReference.class.getResourceAsStream("/static/json/category.json");

            try {
                TypeReference<List<Category>> typeReferenceCustomer  = new TypeReference<List<Category>>() {};
                categoryRepository.deleteAll();
                List<Category> categories = mapper.readValue(inputStream, typeReferenceCustomer);
                categoryRepository.saveAll(categories);
            } catch (IOException e){
                logger.error(e.getMessage());
            }

            inputStream = TypeReference.class.getResourceAsStream("/static/json/product.json");

            try {
                TypeReference<List<Product>> typeReferenceCustomer  = new TypeReference<List<Product>>() {};
                productRepository.deleteAll();
                List<Product> products = mapper.readValue(inputStream, typeReferenceCustomer);
                productRepository.saveAll(products);
            } catch (IOException e){
                logger.error(e.getMessage());
            }

            inputStream = TypeReference.class.getResourceAsStream("/static/json/product_category.json");

            try {
                TypeReference<List<ProductCategory>> typeReferenceCustomer  = new TypeReference<List<ProductCategory>>() {};
                productCategoryRepository.deleteAll();
                List<ProductCategory> productCategory = mapper.readValue(inputStream, typeReferenceCustomer);
                productCategoryRepository.saveAll(productCategory);
            } catch (IOException e){
                logger.error(e.getMessage());
            }
        };
    };
}
