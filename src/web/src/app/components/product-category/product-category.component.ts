import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Grid, ColDef, FirstDataRenderedEvent, GridReadyEvent, IDetailCellRendererParams, GridApi, ColumnApi } from 'ag-grid-community';
import { ProductCategory } from './../../model/product.category';
import { ProductCategoryService } from './../../service/product.category.service';

@Component({
  selector: 'app-product-category',
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.css']
})
export class ProductCategoryComponent implements OnInit {

  public productCategories: ProductCategory[] = [];
  public columnDefs: ColDef[] = [];
  private api: GridApi = new GridApi;
  private columnApi: ColumnApi = new ColumnApi;
  public productCategory: ProductCategory = new ProductCategory;

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
    this.findAllProducts();
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
    }
    ]
  }

  public findAllProducts():void{
    this.productCategoryService.findAllProductsByCategories().subscribe(
      (response: ProductCategory[]) => {
        this.productCategories = response;
      }
    );
  }

}
