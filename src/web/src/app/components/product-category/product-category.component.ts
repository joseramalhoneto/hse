import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Grid, ColDef, FirstDataRenderedEvent, GridReadyEvent, IDetailCellRendererParams, GridApi, ColumnApi } from 'ag-grid-community';
import { ProductCategory } from './../../model/product.category';
import { ProductCategoryService } from './../../service/product.category.service';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-product-category',
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.css']
})
export class ProductCategoryComponent implements OnInit {

  public columnDefs: ColDef[] = [];
  private api: GridApi = new GridApi;
  private columnApi: ColumnApi = new ColumnApi;
  public productCategory!: ProductCategory;
  public productCategories: ProductCategory[] = [];

  public defaultColDef: ColDef = {
    editable: false,
    sortable: true,
    flex: 1,
    minWidth: 100,
    filter: true,
    resizable: true,
  };

  constructor(private productCategoryService: ProductCategoryService) {
    this.columnDefs = this.createColumnDefs();
  }

  ngOnInit(): void {
    this.findAllProductCategories();
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
        field: 'product.name'
    }, {
        headerName: 'Category',
        field: 'category.name'
    }]
  }

  public saveProductCategory(addForm: NgForm): void {
    this.productCategory.productCategoryId = addForm.value.productCategoryId;
    this.productCategory.product.productId = addForm.value.productId;
    this.productCategory.category.categoryId = addForm.value.categoryId;

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
      this.productCategoryService.deleteProductCategoryById(selectedRows[0].productId).subscribe(data => {
        this.findAllProductCategories();
        this.api.refreshCells();
      });
    }
  }

}
