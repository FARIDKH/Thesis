

import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ProductCategoryInterface } from 'src/app/interfaces/productCategory.interface';
import { ProductService } from 'src/app/services/product.service';
import { ProductCategoryService } from 'src/app/services/productCategory.service';

@Component({
    selector: 'app-create-product',
    templateUrl: './createProduct.component.html',
    styleUrls: ['./createProduct.component.scss']
})
export class CreateProductComponent {

    pcs! : [ProductCategoryInterface] 
    constructor(
        private productService : ProductService,
        private productFormBuilder : FormBuilder,
        private productCategoryService : ProductCategoryService,private cd: ChangeDetectorRef)  {
        productCategoryService.readProductCategoryFromDB().subscribe(res => {
            this.pcs = res
        })
    }

    selectedImg?: File;

    onFileChanged(event:any) {
        this.productForm.patchValue({
          productImage: event.target.files[0]
        });
      }
  

      

    productForm = this.productFormBuilder.group({
        title :"",
        description: "",
        stock : 0,
        price : 0,
        productCategoryId : 0,
        constant : "",
        productImage: []
    })

    requestToCreateProduct(){
        let formData : FormData = new FormData()
        Object.keys(this.productForm.controls).forEach(key => {
          if(this.productForm.controls[key].value != null){
            formData.append(key,this.productForm.controls[key].value);
          }
          
        });

        formData.forEach((value,key) => {
          console.log(key+" "+value)
        });
        this.productService.createProduct(formData)
    }


}
