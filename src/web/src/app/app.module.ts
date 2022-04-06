import { NgModule, NO_ERRORS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AgGridModule } from 'ag-grid-angular';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductComponent } from './components/product/product.component';
import { CategoryComponent } from './components/category/category.component';
import { ProductService } from './service/product.service';
import { CategoryService } from './service/category.service';
import { ProductCategoryService } from './service/product.category.service';
import { HttpClientModule } from '@angular/common/http';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { FormsModule } from '@angular/forms';
import { ProductCategoryComponent } from './components/product-category/product-category.component';
import { AngularMultiSelectModule } from 'angular2-multiselect-dropdown';
@NgModule({
  declarations: [
    AppComponent,
    CategoryComponent,
    ProductComponent,
    ProductCategoryComponent,
    NavBarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    AngularMultiSelectModule,
    AgGridModule.withComponents([])
  ],
  schemas:[NO_ERRORS_SCHEMA],
  providers: [ProductService,
    CategoryService,
    ProductCategoryService
  ],
  exports: [AppRoutingModule],
  bootstrap: [AppComponent]
})
export class AppModule { }
