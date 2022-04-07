import { Currency } from './../../model/currency';
import { HttpClient } from '@angular/common/http';
import { Grid, ColDef, FirstDataRenderedEvent, GridReadyEvent, IDetailCellRendererParams, GridApi, ColumnApi } from 'ag-grid-community';
import { Product } from '../../model/product';
import { ProductCategory } from './../../model/product.category';
import { ProductCategoryService } from './../../service/product.category.service';
import { ProductCategoryComponent } from './../product-category/product-category.component';
import { ProductService } from '../../service/product.service';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  public products: Product[] = [];
  public currency = new Currency;
  public columnDefs: ColDef[] = [];
  private api: GridApi = new GridApi;
  private columnApi: ColumnApi = new ColumnApi;
  private action?: string;
  public product: Product = new Product;
  public showCurrencyModal: boolean = false;
  public productCategories: ProductCategory[] = [];

  public defaultColDef: ColDef = {
    editable: false,
    sortable: true,
    flex: 1,
    minWidth: 100,
    filter: true,
    resizable: true,
  };

  constructor(private productService: ProductService,
    private productCategoryService: ProductCategoryService) {
    this.columnDefs = this.createColumnDefs();
  }

  ngOnInit(): void {
    this.findAllProducts();
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
       field: "productId"
    },{
        headerName: 'Name',
        field: 'name'
    },{
      headerName: 'Description',
      field: 'description'
    },{
        headerName: 'Price',
        field: 'price',
        colId: 'price',
        valueGetter: function(params: any){
          return params.data.price + " " + params.data.base
        }
    }
    ]
  }


  public onCellClicked(event: any){
    if (event.column.colId == "price"){
      this.productService.getExchangedValue().subscribe(
        (response: Currency) => {
          this.currency = response;
        }
      );
      this.setShowCurrencyModal();
    }
  }

  public setShowCurrencyModal(){
    this.showCurrencyModal = !this.showCurrencyModal;
  }

  public findAllProducts():void{
    this.productService.findAllProducts().subscribe(
      (response: Product[]) => {
        this.products = response;
      }
    );
  }

  public saveProduct(addForm: NgForm): void {
    this.product.productId = addForm.value.productId;
    this.product.name = addForm.value.name;
    this.product.price = addForm.value.price;

    if(this.action == "add"){
      this.addProduct(this.product);
    }else{
      this.updateProduct(this.product);
    }
    addForm.reset();
  }

  public addProduct(product: Product): void {
    this.productService.saveProduct(product).subscribe(
      () => {
        this.findAllProducts();
        this.api.refreshCells();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

  }

  public updateProduct(product: Product): void {
    this.productService.updateProduct(product).subscribe(
      () => {
        this.findAllProducts();
        this.api.refreshCells();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public deleteProduct(): void {
    var selectedRows = this.api.getSelectedRows();
    if (selectedRows.length == 0) {
      alert("Please select a product");
      return;
    }
    if (confirm("Are you sure you want to delete?")) {
      this.productService.deleteProductById(selectedRows[0].productId).subscribe(data => {
        this.findAllProducts();
        this.api.refreshCells();
      });
    }
  }

  setAction(action: string){
    this.action = action;
  }

  setProduct(): void {
    var selectedRow = this.api.getSelectedRows()[0];
    if (selectedRow == null) {
      alert("Please select a product");
      return;
    }

    this.product = new Product(
      selectedRow.productId,
      selectedRow.name,
      selectedRow.description,
      selectedRow.price,
      selectedRow.base
    )
  }

  public findAllProductCategories(): void{
    this.productCategoryService.findAllProductsByCategories().subscribe(
      (response: ProductCategory[]) => {
        this.productCategories = response;

    this.getCategoriesByProductId(1);
      }
    );
  }

  public getCategoriesByProductId(productId: number){
    var itens = this.productCategories.filter(item => item.product?.productId == productId);
console.log(itens);



  }

}
