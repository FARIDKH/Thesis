
import { Component, ElementRef, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { element } from 'protractor';
import { AuthService } from '../auth/auth.service';
import { OrderItemInterface } from '../interfaces/order-item.interface';
import { BasketService } from '../services/basket.service';
import { ProductService } from '../services/product.service';

@Component({
    selector: 'app-basket',
    templateUrl: './basket.component.html',
    styleUrls: ['./basket.component.scss']
})
export class BasketComponent implements OnInit {
    constructor(
        public basketService : BasketService,
        private authService : AuthService,
        private productService: ProductService,
        private sanitizer: DomSanitizer,
        private elem : ElementRef
    ) { }

    ngOnInit(): void { 
        let loggedInUserId = parseInt(this.authService.getAuthUserId()!)
        this.basketService.getUserBasket(loggedInUserId).subscribe(res => {
            this.basketItems = res
            res.forEach(orderItem => {
                let amount : number = orderItem.amount
                let productPrice  = orderItem.product.price!
                this.total = this.total + amount * productPrice
            });
            res.forEach(orderItem => {
                this.productService.getProductImg(orderItem.product)
            })
        })
    }
    total : number = 0
    showSuccessMessage = false


    basketItems : OrderItemInterface[] = []

    readUserBasket(){
        let loggedInUserId = parseInt(this.authService.getAuthUserId()!)
        this.basketService.getUserBasket(loggedInUserId).subscribe(res => {
            this.basketItems = res
            this.total = 0
            res.forEach(orderItem => {
                let amount : number = orderItem.amount
                let productPrice  = orderItem.product.price!
                this.total = this.total + amount * productPrice
            });

            res.forEach(orderItem => {
                this.productService.getProductImg(orderItem.product)
            })
            
        })
    }


    deleteFromBasket(id: Number){
        this.basketService.deleteItemFromBasket(id).subscribe((res)=> {
            this.readUserBasket()
        })
    }

  

    sendOrder(){
        let loggedInUserId = parseInt(this.authService.getAuthUserId()!)
        this.basketService.processOrder(loggedInUserId).subscribe(res => {
            this.showSuccessMessage = true
            this.basketItems = []
            setTimeout(() => {
                this.showSuccessMessage = false
            }, 3000);
        })
        
    }


}
