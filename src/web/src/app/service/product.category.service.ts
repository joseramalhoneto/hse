import { ProductCategory } from './../model/product.category';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductCategoryService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public findAllProductsByCategories(): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/productCategory/find/all`);
  }

  public saveProductCategory(productCategory: ProductCategory): Observable<any>{
    return this.http.post(`${this.apiServerUrl}/productCategory/save`, productCategory);
  }

  public deleteProductCategoryById(productCategoryId: number){
    return this.http.delete(`${this.apiServerUrl}/productCategory/delete/${productCategoryId}`);
  }

}
