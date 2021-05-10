

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductInfoComponent } from './product-info.component';
import { BasketService } from '../services/basket.service';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RateService } from '../services/rate.service';

@NgModule({
    declarations: [ProductInfoComponent],
    imports: [ BrowserModule,CommonModule, FormsModule, ReactiveFormsModule ],
    exports: [ProductInfoComponent],
    providers: [BasketService,RateService],
})
export class ProductInfoModule {}