import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Product } from '../model/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public findAllProducts(): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/product/find/all`);
  }

  public findProductById(productId: number): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/product/find/${productId}`);
  }

  public saveProduct(product: Product): Observable<any>{
    return this.http.post(`${this.apiServerUrl}/product/save`, product);
  }

  public updateProduct(Product: Product): Observable<any>{
    return this.http.put(`${this.apiServerUrl}/product/update`, Product);
  }

  public deleteProductById(productId: number){
    return this.http.delete(`${this.apiServerUrl}/product/delete/${productId}`);
  }

  public findAllProductsByCategory(): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/product/find/allProductsByCategory`);
  }

}
