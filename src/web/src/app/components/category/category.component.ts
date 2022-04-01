import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Grid, ColDef, FirstDataRenderedEvent, GridReadyEvent, IDetailCellRendererParams, GridApi, ColumnApi } from 'ag-grid-community';
import { Category } from './../../model/category';
import { CategoryService } from './../../service/category.service';
import { NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  public categories: Category[] = [];
  public columnDefs: ColDef[] = [];
  private api: GridApi = new GridApi;
  private columnApi: ColumnApi = new ColumnApi;
  private action?: string;
  public category: Category = new Category;

  public defaultColDef: ColDef = {
    editable: false,
    sortable: true,
    flex: 1,
    minWidth: 100,
    filter: true,
    resizable: true,
  };

  constructor(private categoryService: CategoryService) {
    this.columnDefs = this.createColumnDefs();
  }

  ngOnInit(): void {
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
       field: "categoryId"
    },{
        headerName: 'Name',
        field: 'name'
    }
    ]
  }

  public findAllCategories():void{
    this.categoryService.findAllCategories().subscribe(
      (response: Category[]) => {
        this.categories = response;
      }
    );
  }

  public saveCategory(addForm: NgForm): void {
    this.category.categoryId = addForm.value.categoryId;
    this.category.name = addForm.value.name;

    if(this.action == "add"){
      this.addCategory(this.category);
    }else{
      this.updateCategory(this.category);
    }
    addForm.reset();
  }

  public addCategory(category: Category): void {
    this.categoryService.saveCategory(category).subscribe(
      () => {
        this.findAllCategories();
        this.api.refreshCells();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

  }

  public updateCategory(category: Category): void {
    this.categoryService.updateCategory(category).subscribe(
      () => {
        this.findAllCategories();
        this.api.refreshCells();
      },(error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public deleteCategory(): void {
    var selectedRows = this.api.getSelectedRows();
    if (selectedRows.length == 0) {
      alert("Please select a category");
      return;
    }
    if (confirm("Are you sure you want to delete?")) {
      this.categoryService.deleteCategoryById(selectedRows[0].categoryId).subscribe(data => {
        this.findAllCategories();
        this.api.refreshCells();
      });
    }
  }

  setAction(action: string){
    this.action = action;
  }

  setCategory(): void {
    var selectedRow = this.api.getSelectedRows()[0];
    if (selectedRow == null) {
      alert("Please select a category");
      return;
    }

    this.category = new Category(
      selectedRow.categoryId,
      selectedRow.name
    )
  }

}
