package de.hse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_category")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "product_category_id")
    private long productCategoryId;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "category_id")
    private long categoryId;

    @Transient
    @Column(name = "product_name")
    private String productName;

    @Transient
    @Column(name = "category_name")
    private String categoryName;

//    @ManyToOne
//    private Product product;
//
//    @ManyToOne
//    private Category category;
}
