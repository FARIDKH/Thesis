

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShopComponent } from './shop.component';
import { HttpClientModule } from '@angular/common/http';
import { ProductCategoryService } from '../services/productCategory.service';
import { FormsModule } from '@angular/forms';
import { RateService } from '../services/rate.service';

@NgModule({
    declarations: [ShopComponent],
    imports: [ CommonModule, HttpClientModule, FormsModule],
    exports: [ShopComponent],
    providers: [ProductCategoryService,RateService],
})
export class ShopModule {}