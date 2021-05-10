

import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { ProductInterface } from '../interfaces/product.interface';
import { ProductCategoryInterface } from '../interfaces/productCategory.interface';
import { ProductService } from '../services/product.service';
import { ProductCategoryService } from '../services/productCategory.service';
import { RateService } from '../services/rate.service';

@Component({
    selector: 'app-shop',
    templateUrl: './shop.component.html',
    styleUrls : ['./shop.component.scss'],
    
})
export class ShopComponent implements OnInit {
    constructor(
        private productService: ProductService,
        private productCategoryService : ProductCategoryService,
        private router: Router,
        private sanitizer : DomSanitizer,
        public rateService: RateService
    ) { }


    productCategories : ProductCategoryInterface[] = []
    allProducts : ProductInterface[] = []

    pcFilters : ProductCategoryInterface[] = []

    minPriceFilter : Number = 0
    maxPriceFilter : Number = 0
    initMaxPriceFilter : Number = 0

    productCategoryFilteredProducts : ProductInterface[] = []
    priceFilteredProducts : ProductInterface[] = []
        
    priceFilterIsSelected : Boolean = false
    productCategoryFilterIsSelected : Boolean = false


    ngOnInit(): void {
        this.productCategoryService.readProductCategoryFromDB().subscribe((res) => {
            this.productCategories = res
        })
        this.productService.readAllProducts().subscribe((res) => {
            this.allProducts = res
            
            res.forEach(p => {
                this.productService.getProductImg(p)
                this.rateService.getRate(p.seller?.id!).subscribe(rate => {
                    p.seller!.rating = rate
                })
            })
        })

        this.productService.getMinAndMaxPrice().subscribe((res) => {
            this.minPriceFilter = res.min
            this.maxPriceFilter = res.max
            this.initMaxPriceFilter = res.max
        })


    }

    fix(num?:Number){
        return num?.toFixed(1)
    }
    

    addPCFilter(pcID: ProductCategoryInterface){
        if (this.pcFilters.indexOf(pcID) > -1){ 
            this.pcFilters.splice(this.pcFilters.indexOf(pcID,0),1)
        } else {
            this.pcFilters.push(pcID)
        }
        if (this.pcFilters.length != 0) {
            this.allProducts = []
            this.productCategoryFilterIsSelected = true;
        } else {
            if(this.priceFilterIsSelected){
                this.applyPriceFilter()
            } else {
                this.productService.readAllProducts().subscribe((res) => {
                    this.allProducts = res
                    res.forEach(p => {
                        this.productService.getProductImg(p)
                    })
                })
            }
            
            this.productCategoryFilterIsSelected = false;
        }
        
        this.applyProductCategoryFilter();
    }
    
    applyProductCategoryFilter(){
        
        
        this.productCategoryFilteredProducts = []
        this.pcFilters.forEach(pc => {
            this.productService
                .readAllProductsByProductCategoryId(pc.id).subscribe((res) => {
                    res.forEach(product => {
                        if (!this.productCategoryFilteredProducts.includes(product)){
                            this.productCategoryFilteredProducts.push(product)
                        }
                        this.rateService.getRate(product.seller?.id!).subscribe(rate => {
                            product.seller!.rating = rate
                        })
                    });
                    
                    res.forEach(p => {
                        this.productService.getProductImg(p)
                    })

                    if(this.priceFilterIsSelected && this.productCategoryFilterIsSelected){
                        this.applyBothFilter()
                    } else {
                        this.allProducts = this.productCategoryFilteredProducts
                    }

                })
        });

        
        

    }

    applyPriceFilter(){
        this.priceFilterIsSelected = true

        this.productService
            .readAllProductsBetweenMinAndMax(this.minPriceFilter,this.maxPriceFilter)
            .subscribe((res) => {
                this.priceFilteredProducts = res

                if(this.priceFilterIsSelected && this.productCategoryFilterIsSelected){
                    this.applyBothFilter()
                } else {
                    console.log(this.priceFilteredProducts)
                    this.allProducts = this.priceFilteredProducts
                }
                this.priceFilteredProducts.forEach(r => {
                    this.rateService.getRate(r.seller?.id!).subscribe(rate => {
                        r.seller!.rating = rate
                    })
                    this.productService.getProductImg(r)
                });
            })
        

    }


    mappingFunc(pID? : Number) : Boolean{
        var prodIsFound = false
        var i = 0;
        while(!prodIsFound && i < this.priceFilteredProducts.length){
            if(this.priceFilteredProducts[i].id == pID){
                prodIsFound = true;
            } else {
                i++;
            }
            
        }

        return prodIsFound;
    }

    applyBothFilter(){
        const filteredArray = this.productCategoryFilteredProducts.filter(value => this.mappingFunc(value.id));
    }

    goToProductInfo(id?: Number){
        this.router.navigateByUrl("/product/" + id)
    }
    

    
    

}
