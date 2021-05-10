

import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { ProductInterface } from '../interfaces/product.interface';
import { BasketService } from '../services/basket.service';
import { ProductService } from '../services/product.service';
import { RateService } from '../services/rate.service';

@Component({
    selector: 'app-product-info',
    templateUrl: './product-info.component.html',
    styleUrls: ['./product-info.component.scss']
})
export class ProductInfoComponent implements OnInit, OnDestroy {
    constructor(
        private activatedRoute : ActivatedRoute,
        private authService: AuthService,
        private router: Router,
        private productService : ProductService,
        private sanitizer: DomSanitizer,
        public basketService : BasketService,
        public rateService: RateService
        ) { }
    
    public product?: ProductInterface;
    public productId : Number = 0
    public productAmount : Number = 0
    sameCategoryProducts : ProductInterface[] = []

    private sub: any;
    public rateForm = new FormGroup({
        rating: new FormControl()
     });

    ngOnInit(): void { 
        

        this.sub = this.activatedRoute.params.subscribe(params => {
            console.log(params)
            let id = parseInt(params['id'])
            this.productId = id
            
            this.productService.readProductById(id).subscribe(res => {
                this.product = res
                this.productService.getProductImg(res)
                this.rateService.getRate(this.product.seller?.id!).subscribe(res => {
                    this.rateForm.get('rating')?.setValue(res.toString())
                })
                this.productService.readAllProductsByProductCategoryId(res.productCategory.id!).subscribe((res) => {
                    this.sameCategoryProducts = res
                    res.forEach(p => {
                        this.productService.getProductImg(p)
                    })
                })
            })
            
            
        })

    }

    ngOnDestroy(){
        this.sub.unsubscribe();
    }

    addProductToBasket(){
        let loggedInUserId = parseInt(this.authService.getAuthUserId()!)
        this.basketService.addProductToBasket(this.productId,this.productAmount,loggedInUserId)
    }

    goToProductInfo(id?:Number){
        this.router.navigateByUrl("/product/"+id)
    }

    rate(event:any,sellerId:number){

        
        if(event.target.id){
            let rateValue = this.getRateValue(event.target.id)
            if(this.authService.isUserLoggedIn()){
                this.rateService.rateSeller(
                    parseInt(this.authService.getAuthUserId()!),
                    sellerId,
                    rateValue
                ).subscribe(res => {
                    
                })
            }
            
        }

    }
    
    getRateValue(rateRawVal:string): number{
        var rateValue = 0;
        rateRawVal = rateRawVal.replace("star","")
        rateRawVal = rateRawVal.replace("half",".5")
        rateValue = parseFloat(rateRawVal)
        return rateValue
    }

    

}
