
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminComponent } from './admin.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AdminService } from '../services/admin.service';
import { HttpInterceptorService } from '../helpers/httpinterceptor.service';
import { ProductCategoryService } from '../services/productCategory.service';

@NgModule({
    declarations: [AdminComponent],
    imports: [ CommonModule, FormsModule , ReactiveFormsModule, HttpClientModule],
    exports: [AdminComponent],
    providers: [AdminService, ProductCategoryService,
        { provide: HTTP_INTERCEPTORS, useClass: HttpInterceptorService, multi: true }
    ],
})
export class AdminModule {}