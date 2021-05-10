import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { OrderItemInterface } from "../interfaces/order-item.interface";



@Injectable()
export class BasketService {

    basketProductCount  = 0

    

    constructor(private httpClient: HttpClient){
        const basketCountStr = sessionStorage.getItem("BASKET_COUNT")
        if (basketCountStr != null){
            this.basketProductCount = parseInt(basketCountStr)
        } else {
            sessionStorage.setItem("BASKET_COUNT", "0");
        }
    }

    increaseCount(){
        this.basketProductCount++;
        this.saveToSessionStorage();
    }

    decreaseCount(){
        this.basketProductCount--;
        this.saveToSessionStorage();
    }

    saveToSessionStorage(){
        sessionStorage.setItem("BASKET_COUNT",this.basketProductCount.toString());
    }

    addProductToBasket(productId: Number,productAmount : Number ,userId: Number){
        this.httpClient.post(
            environment.host +  "/order/user/"+userId+"/product/"+productId+"/product_amount/" + productAmount,
            {}
        ).subscribe(res => {
            console.log(res)
        })
    }

    getUserBasket(userId: Number) : Observable<OrderItemInterface[]>{
        return this.httpClient.get<OrderItemInterface[]>(
            environment.host + "/order/user/"+userId+"/findAllInBasket"
        )
    }

    deleteItemFromBasket(orderItemId: Number){
        return this.httpClient.delete(
            environment.host + "/order/orderItem/" + orderItemId
        )
    }

    processOrder(userId: Number){
        return this.httpClient.put(
            environment.host + "/order/user/" + userId + "/changeStatusToOrdered",
            {}
        );
    }

    getUserOrders(userId: Number) : Observable<OrderItemInterface[]>{
        return this.httpClient.get<OrderItemInterface[]>(
            environment.host + "/order/user/"+userId+"/findAll"
        )
    }

    getRequestedOrders(sellerId:Number){
        return this.httpClient.get<OrderItemInterface[]>(
            environment.host + "/order/seller/" + sellerId + "/status/ORDERED/getAllOrders"
        )
    }

    getAcceptedOrders(sellerId:Number){
        return this.httpClient.get<OrderItemInterface[]>(
            environment.host + "/order/seller/" + sellerId + "/status/ACCEPTED/getAllOrders"
        )
    }
    
    getRejectedOrders(sellerId:Number){
        return this.httpClient.get<OrderItemInterface[]>(
            environment.host + "/order/seller/" + sellerId + "/status/REJECTED/getAllOrders"
        )
    }


    acceptRequestedOrder(sellerId: Number, orderItemId: Number){
        return this.httpClient.put<OrderItemInterface[]>(
            environment.host + "/order/seller/"+sellerId+"/orderItem/"+orderItemId+"/acceptOrder",
            {}
        )
    }

    declineRequestedOrder(sellerId: Number, orderItemId: Number){
        return this.httpClient.put<OrderItemInterface[]>(
            environment.host + "/order/seller/"+sellerId+"/orderItem/"+orderItemId+"/declineOrder",
            {}
        )
    }


}