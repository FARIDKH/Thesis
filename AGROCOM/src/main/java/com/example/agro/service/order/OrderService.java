package com.example.agro.service.order;


import com.example.agro.models.*;
import com.example.agro.repositories.order.OrderRepository;
import com.example.agro.service.orderItem.OrderItemService;
import com.example.agro.service.product.ProductService;
import com.example.agro.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    public Iterable<Order> findAll(){
        return this.orderRepository.findAll();
    }


    public Order addToBasket(Long customer_id, Long product_id,Long product_amount){
        User customer = this.userService.getUserByID(customer_id).get();
        Optional<Order> customer_order = this.orderRepository.findByCustomerIdAndStatus(customer_id,Status.BASKET);
        Order order;
        if(customer_order.isEmpty()){
            order = new Order();
            order.setCustomer(customer);
            orderRepository.save(order);
        } else {
            order = customer_order.get();
        }

        Product product = productService.getProduct(product_id).get();

        orderItemService.createOrderItem(product,product_amount,order);
        order.setDate(new Date(new java.util.Date().getTime()));
        order.setPaymentMethod(PaymentMethod.CASH);
        order.setStatus(Status.BASKET);
        return this.orderRepository.save(order);
    }

    public boolean deleteFromBasket(Long orderItemId){
        return this.orderItemService.deleteOrderItem(orderItemId);
    }

    public List<OrderItem> getUserOrders(Long id){
        return this.orderItemService.getAllOrderItemsOfCustomer(id);
    }

    public List<OrderItem> getSellerOrders(Long seller_id,Status status){
        return this.orderItemService.getSellerOrders(seller_id,status);
    }

    public List<OrderItem> getUserBasket(Long id){
        return this.orderItemService.getUserBasket(id);
    }


    public boolean changeStatusToOrdered(Long user_id){
        Optional<Order> order =  this.orderRepository.findByCustomerIdAndStatus(user_id,Status.BASKET);
        if(order.isEmpty()){
            return false;
        } else {
            order.get().setStatus(Status.ORDERED);
            this.orderRepository.save(order.get());
            return true;
        }
    }



}
