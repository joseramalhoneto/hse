import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Category } from '../model/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public findAllCategories(): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/category/find/all`);
  }

  public findCategoryById(categoryId: number): Observable<any>{
    return this.http.get(`${this.apiServerUrl}/category/find/${categoryId}`);
  }

  public saveCategory(category: Category): Observable<any>{
    return this.http.post(`${this.apiServerUrl}/category/save`, category);
  }

  public updateCategory(Category: Category): Observable<any>{
    return this.http.put(`${this.apiServerUrl}/category/update`, Category);
  }

  public deleteCategoryById(categoryId: number){
    return this.http.delete(`${this.apiServerUrl}/category/delete/${categoryId}`);
  }

}
