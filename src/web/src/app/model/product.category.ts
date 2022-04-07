import { Category } from './category';
import { Product } from './product';

export class ProductCategory {
  productCategoryId?: number;
  categoryId?: number;
  productId?: number;
  product?: Product;
  category?: Category;

  constructor(
      productCategoryId?: number,
      categoryId?: number,
      productId?: number,
      product?: Product,
      category?: Category
    ) {
        this.productCategoryId = productCategoryId;
        this.productId = productId;
        this.categoryId = categoryId;
        this.product = product;
        this.category = category;
      }
}
