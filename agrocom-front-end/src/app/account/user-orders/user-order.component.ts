

import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { AuthService } from 'src/app/auth/auth.service';
import { OrderItemInterface } from 'src/app/interfaces/order-item.interface';
import { BasketService } from 'src/app/services/basket.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
    selector: 'app-user-order',
    templateUrl: './user-order.component.html',
    styleUrls: ['./user-order.component.scss']
})
export class UserOrdersComponent implements OnInit {
    constructor(
        private basketService : BasketService,
        private authService: AuthService,
        private productService : ProductService,
        private sanitizer : DomSanitizer
    ) { }

    authIsSeller = false
    orders : OrderItemInterface[] = []
    requestedOrders: OrderItemInterface[] = []
    acceptedOrders: OrderItemInterface[] = []
    rejectedOrders: OrderItemInterface[] = []

    ngOnInit(): void {
        this.basketService.getUserOrders(parseInt(this.authService.getAuthUserId()!)).subscribe(res => {
            this.orders = res
            res.forEach(orderItem => {
                this.productService.getProductImg(orderItem.product)
            })
        })
        this.authService.getAuthUser().subscribe(res => {
            res.authorities.forEach((el:any) => {
                if (el.authority.includes("SELLER")){
                    this.authIsSeller = true
                }
            });
            if (this.authIsSeller){
                this.basketService.getRequestedOrders(parseInt(this.authService.getAuthUserId()!)).subscribe(res => {
                    this.requestedOrders = res
                    res.forEach(orderItem => {
                        this.productService.getProductImg(orderItem.product)
                    })
                })
                this.basketService.getAcceptedOrders(parseInt(this.authService.getAuthUserId()!)).subscribe(res => {
                    this.acceptedOrders = res
                    res.forEach(orderItem => {
                        this.productService.getProductImg(orderItem.product)
                    })
                })
                this.basketService.getRejectedOrders(parseInt(this.authService.getAuthUserId()!)).subscribe(res => {
                    this.rejectedOrders = res
                    res.forEach(orderItem => {
                        this.productService.getProductImg(orderItem.product)
                    })
                })
            }
        })
     }

    readOrders(){
        this.basketService.getUserOrders(parseInt(this.authService.getAuthUserId()!)).subscribe(res => {
            this.orders = res
            res.forEach(orderItem => {
                this.productService.getProductImg(orderItem.product)
            })
        })
        if (this.authIsSeller){
            this.basketService.getRequestedOrders(parseInt(this.authService.getAuthUserId()!)).subscribe(res => {
                this.requestedOrders = res
                res.forEach(orderItem => {
                    this.productService.getProductImg(orderItem.product)
                })
            })
            this.basketService.getAcceptedOrders(parseInt(this.authService.getAuthUserId()!)).subscribe(res => {
                this.acceptedOrders = res
                res.forEach(orderItem => {
                    this.productService.getProductImg(orderItem.product)
                })
            })
            this.basketService.getRejectedOrders(parseInt(this.authService.getAuthUserId()!)).subscribe(res => {
                this.rejectedOrders = res
                res.forEach(orderItem => {
                    this.productService.getProductImg(orderItem.product)
                })
            })
        }
    }
    acceptOrder(orderItemId:Number){
        let authId = this.authService.getAuthUserId()!
        this.basketService.acceptRequestedOrder(parseInt(authId),orderItemId).subscribe(res => {
            this.readOrders()
        })
    }

    declineOrder(orderItemId:Number){
        let authId = this.authService.getAuthUserId()!
        this.basketService.declineRequestedOrder(parseInt(authId),orderItemId).subscribe(res => {
            this.readOrders()
        })
    }


    
}
