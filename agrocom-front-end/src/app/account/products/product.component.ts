
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ProductInterface } from 'src/app/interfaces/product.interface';
import { ProductService } from 'src/app/services/product.service';

@Component({
    selector: 'app-product',
    templateUrl: './product.component.html',
    styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {
    constructor(public productService : ProductService, private sanitizer: DomSanitizer) { 
        this.productService.readAllUserProducts().subscribe((res) => {
            this.products = res;

            res.forEach(p => {
                this.productService.getProductImg(p)
            })
        })
    }

    ngOnInit(): void { }
    
    action : String = "myproducts"

    products : ProductInterface[] = []


    changeAction(action: String){
        this.action = action
        if (action == "myproducts"){
            this.productService.readAllUserProducts().subscribe((res) => {
                this.products = res;
                res.forEach(p => {
                    this.productService.getProductImg(p)
                })
            })
        }
    }

    

}
