package com.example.agro.rest.order;


import com.example.agro.models.Order;
import com.example.agro.models.OrderItem;
import com.example.agro.models.Status;
import com.example.agro.service.order.OrderService;
import com.example.agro.service.orderItem.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/findAll")
    @ResponseBody
    public Iterable<Order> findAll(){
        return this.orderService.findAll();
    }

    @GetMapping("/user/{user_id}/findAll")
    @ResponseBody
    public Iterable<OrderItem> findUsersOrderItems(
            @PathVariable Long user_id
            ){
        return this.orderService.getUserOrders(user_id);
    }

    @GetMapping("/user/{user_id}/findAllInBasket")
    @ResponseBody
    public Iterable<OrderItem> findAllInBasket(
            @PathVariable Long user_id
    ){
        return this.orderService.getUserBasket(user_id);
    }

    @PostMapping("/user/{user_id}/product/{product_id}/product_amount/{product_amount}")
    @ResponseBody
    public Order addToBasket(
            @PathVariable Long user_id,
            @PathVariable Long product_id,
            @PathVariable Long product_amount
            ){
        return this.orderService.addToBasket(user_id,product_id,product_amount);
    }


    @DeleteMapping("/orderItem/{orderItemId}")
    @ResponseBody
    public boolean deleteFromBasket(
            @PathVariable Long orderItemId
    ){
        this.orderService.deleteFromBasket(orderItemId);
        return true;
    }

    @PutMapping("/user/{user_id}/changeStatusToOrdered")
    @ResponseBody
    public void updateOrderItemStatus(
            @PathVariable Long user_id
    ){
        this.orderService.changeStatusToOrdered(user_id);
    }

    @GetMapping("/seller/{sellerId}/status/{status}/getAllOrders")
    @ResponseBody
    public List<OrderItem> getAllOrders(
            @PathVariable Long sellerId,
            @PathVariable Status status
    ){
        return this.orderService.getSellerOrders(sellerId,status);
    }

    @PutMapping("/seller/{sellerId}/orderItem/{orderItemId}/acceptOrder")
    @ResponseBody
    public boolean acceptOrder(
            @PathVariable Long sellerId,
            @PathVariable Long orderItemId
    ){
        return this.orderItemService.acceptOrderItem(sellerId,orderItemId);
    }

    @PutMapping("/seller/{sellerId}/orderItem/{orderItemId}/declineOrder")
    @ResponseBody
    public boolean declineOrder(
            @PathVariable Long sellerId,
            @PathVariable Long orderItemId
    ){

        return this.orderItemService.declineOrderItem(sellerId,orderItemId);
    }



}
