

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AdminService } from '../services/admin.service';
import { ProductCategory, ProductCategoryInterface } from '../interfaces/productCategory.interface';
import { ProductCategoryService } from '../services/productCategory.service';

@Component({
    selector: 'app-admin',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.scss']
})
export class AdminComponent {

    constructor(
        private adminService : AdminService,
        private productCategoryService: ProductCategoryService,
        private productCategoryFormBuilder : FormBuilder) { }


    productCategoryForm : FormGroup = this.productCategoryFormBuilder.group({
        categoryName : "",
        product_category_id : ""
    })

    productCategories: ProductCategory[] = [];


    ngOnInit(): void {
        this.productCategoryService.readProductCategoryFromDB().subscribe((res) => {
            res.forEach(pc => {
                let p  = new ProductCategory(pc.id,pc.title,pc.productCategory)
                this.productCategories.push(p)
            });
            
        })
    }

    updatePC(){
        this.productCategories = []
        this.productCategoryService.readProductCategoryFromDB().subscribe((res) => {
            res.forEach(pc => {
                let p  = new ProductCategory(pc.id,pc.title,pc.productCategory)
                this.productCategories.push(p)
            });
        })
    }

    createProductCategory() : void {
        let formData = this.productCategoryForm
        let categoryName = formData.get(['categoryName'])?.value
        let parentCategory = formData.get(['product_category_id'])?.value
        this.productCategoryService.addProductCategoryToDB(categoryName,parentCategory).subscribe((res) => {
            this.updatePC()
        })
    }

}
