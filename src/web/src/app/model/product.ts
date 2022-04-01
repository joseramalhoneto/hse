export class Product{
  productId?: number;
  name?: string;
  description?: string;
  price?: number;
  base?: string;

  constructor(
    productId?: number,
    name?: string,
    description?: string,
    price?: number,
    base?: string
  ) {
      this.productId = productId,
      this.name = name,
      this.description = description,
      this.price = price,
      this.base = base
    }
}
