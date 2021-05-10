

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateProductComponent } from './createProduct.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProductService } from 'src/app/services/product.service';
import { ProductCategoryService } from 'src/app/services/productCategory.service';

@NgModule({
    declarations: [CreateProductComponent],
    imports: [ CommonModule, FormsModule, ReactiveFormsModule ],
    exports: [CreateProductComponent],
    providers: [ProductService,ProductCategoryService],
})
export class CreateProductModule {}