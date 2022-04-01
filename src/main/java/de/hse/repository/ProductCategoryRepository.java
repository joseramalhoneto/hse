package de.hse.repository;

import de.hse.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository  extends JpaRepository<ProductCategory, Long> {

    @Query(value = "SELECT * FROM product_category GROUP BY product_product_id", nativeQuery = true)
    public List<ProductCategory> getProductByCategory();

}
