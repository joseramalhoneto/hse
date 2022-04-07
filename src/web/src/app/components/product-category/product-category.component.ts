import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Grid, ColDef, FirstDataRenderedEvent, GridReadyEvent, IDetailCellRendererParams, GridApi, ColumnApi } from 'ag-grid-community';
import { ProductCategory } from './../../model/product.category';
import { ProductCategoryService } from './../../service/product.category.service';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Product } from '../../model/product';
import { ProductService } from '../../service/product.service';
import { Category } from './../../model/category';
import { CategoryService } from './../../service/category.service';

@Component({
  selector: 'app-product-category',
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.css']
})
export class ProductCategoryComponent implements OnInit {

  public columnDefs: ColDef[] = [];
  private api: GridApi = new GridApi;
  private columnApi: ColumnApi = new ColumnApi;
  public productCategory: ProductCategory = new ProductCategory;
  public productCategories: ProductCategory[] = [];
  public products: Product[] = [];
  public categories: Category[] = [];

  public defaultColDef: ColDef = {
    editable: false,
    sortable: true,
    flex: 1,
    minWidth: 100,
    filter: true,
    resizable: true,
  };

  constructor(private productCategoryService: ProductCategoryService,
    private productService: ProductService,
    private categoryService: CategoryService) {
    this.columnDefs = this.createColumnDefs();
  }

  ngOnInit(): void {
    this.findAllProductCategories();
    this.findAllProducts();
    this.findAllCategories();
  }

  onGridReady(params: any): void {
    this.api = params.api;
    this.columnApi = params.columnApi;
    this.api.sizeColumnsToFit();
    this.api.setDomLayout("autoHeight");
  }

  private createColumnDefs() {
    return [
      {
       headerName: "Id",
       field: "productCategoryId"
    },{
        headerName: 'Product',
        //field: 'product.name'
        field: 'productId'

    }, {
        headerName: 'Category',
        //field: 'category.name'
        field: 'categoryId'
    }]
  }

  public findAllProducts():void{
    this.productService.findAllProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
      }
    );
  }

  public findAllCategories():void{
    this.categoryService.findAllCategories().subscribe(
      (response: Category[]) => {
        this.categories = response;
      }
    );
  }

  public saveProductCategory(addForm: NgForm): void {

    console.log(addForm.value.productCategoryId);
    this.productCategory.productCategoryId = addForm.value.productCategoryId;
    this.productCategory.productId = addForm.value.productId;
    this.productCategory.categoryId = addForm.value.categoryId;
    console.log(addForm.value);
    this.productCategoryService.saveProductCategory(this.productCategory).subscribe(
      () => {
        this.findAllProductCategories();
        this.api.refreshCells();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

  }

  public findAllProductCategories(): void{
    this.productCategoryService.findAllProductsByCategories().subscribe(
      (response: ProductCategory[]) => {
        this.productCategories = response;
      }
    );
  }

  public deleteProductCategory(){
    var selectedRows = this.api.getSelectedRows();
    if (selectedRows.length == 0) {
      alert("Please select a item");
      return;
    }
    if (confirm("Are you sure you want to delete?")) {
      this.productCategoryService.deleteProductCategoryById(selectedRows[0].productCategoryId).subscribe(data => {
        this.findAllProductCategories();
        this.api.refreshCells();
      });
    }
  }

}
