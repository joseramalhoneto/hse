package de.hse.repository;

import de.hse.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository  extends JpaRepository<ProductCategory, Long> {

    @Query(value = " SELECT distinct(product.name) " +
                    " FROM hse.product, hse.product_category " +
                    " WHERE product.product_id = product_category.product_id " +
                    " AND product.product_id = :productId ", nativeQuery = true)
    public String findProductById(@Param("productId") Long productId);

    @Query(value = " SELECT distinct(category.name) " +
                    " FROM hse.category, hse.product_category " +
                    " WHERE category.category_id = product_category.category_id " +
                    " AND category.category_id = :categoryId ", nativeQuery = true)
    public String findCategoryById(@Param("categoryId") Long categoryId);

}
