import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductComponent } from './product.component';
import { CreateProductModule } from './createProduct/createProduct.module';

@NgModule({
    declarations: [ProductComponent],
    imports: [ CommonModule, CreateProductModule ],
    exports: [ProductComponent],
    providers: [],
})
export class ProductModule {}