package com.example.agro.service.orderItem;


import com.example.agro.models.Order;
import com.example.agro.models.OrderItem;
import com.example.agro.models.Product;
import com.example.agro.models.Status;
import com.example.agro.repositories.orderItem.OrderItemRepository;
import com.example.agro.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItem createOrderItem(Product product,Long product_amount, Order order){
        OrderItem orderItem;

        if(!orderItemRepository.existsByProductIdAndOrderId(product.getId(),order.getId())){
            orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setAmount(product_amount);
            orderItem.setProduct(product);
            orderItem.setCustomer(order.getCustomer());
        } else {
            orderItem =  orderItemRepository.findByProductIdAndOrderId(product.getId(),order.getId()).get();
            orderItem.setAmount(orderItem.getAmount() + product_amount);
        }

        return this.orderItemRepository.save(orderItem);
    }

    public boolean deleteOrderItem(Long orderItemId){
        Optional<OrderItem> orderItem = orderItemRepository.findById(orderItemId);
        if (orderItem.isEmpty()){
            return false;
        } else {
            orderItemRepository.delete(orderItem.get());
            return true;
        }
    }

    public List<OrderItem> getAllOrderItemsOfCustomer(Long customer_id){
        return this.orderItemRepository.findByCustomerId(customer_id).get();
    }

    public List<OrderItem> getUserBasket(Long customer_id){
        List<OrderItem> orderItems= this.orderItemRepository.findByCustomerId(customer_id).get();
        return orderItems.stream().filter(o -> o.getOrder().getStatus() == Status.BASKET)
                .collect(Collectors.toList());
    }

    public List<OrderItem> getSellerOrders(Long seller_id,Status status){
        return this.orderItemRepository.findAllBySellerIdAndStatus(seller_id,status).get();
    }

    public boolean acceptOrderItem(Long sellerId,Long orderItemId){
        OrderItem orderItem = this.orderItemRepository.findById(orderItemId).get();
        if (orderItem.getProduct().getSeller().getId() == sellerId){
            orderItem.setStatus(Status.ACCEPTED);
            this.orderItemRepository.save(orderItem);
            return true;
        }
        return false;
    }


    public boolean declineOrderItem(Long sellerId,Long orderItemId){
        OrderItem orderItem = this.orderItemRepository.findById(orderItemId).get();
        if (orderItem.getProduct().getSeller().getId() == sellerId){
            orderItem.setStatus(Status.REJECTED);
            this.orderItemRepository.save(orderItem);
            return true;
        }
        return false;

    }







}
