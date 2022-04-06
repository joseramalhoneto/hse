import { Category } from './category';
import { Product } from './product';

export class ProductCategory {
  productCategoryId: number;
  product: Product;
  category: Category;

  constructor(
      productCategoryId: number,
      product: Product,
      category: Category
    ) {
        this.productCategoryId = productCategoryId;
        this.product = product;
        this.category = category;
      }
}
