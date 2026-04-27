package com.example.Project.Cart.service;

import com.example.Project.Cart.model.Cart;
import com.example.Project.Cart.model.Order;
import com.example.Project.Cart.model.OrderItem;
import com.example.Project.Cart.repository.CartRepo;
import com.example.Project.Cart.repository.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartRepo cartRepo;

    @Transactional
    public Order processCheckout(Order orderRequest) {
        // 1. Fetch everything from the user's current cart
        List<Cart> cartItems = cartRepo.findAll();

        if(cartItems.isEmpty()) {
            throw new RuntimeException("Cannot checkout an empty cart!");
        }

        // 2. Convert Cart items into OrderItem snapshots
        List<OrderItem> orderItems = new ArrayList<>();
        int finalTotal = 0;

        for (Cart cart : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setProductId(cart.getProductId());
            oi.setDescription(cart.getDescription());
            oi.setQuantity(cart.getQuantity());
            oi.setPricePerUnit(cart.getListPrice());
            orderItems.add(oi);

            finalTotal += cart.getTotCost();
        }

        // 3. Set the calculated values into the order
        orderRequest.setItems(orderItems);
        orderRequest.setTotalBill(finalTotal);

        // 4. Save the Order (This automatically saves OrderItems due to CascadeType.ALL)
        Order savedOrder = orderRepo.save(orderRequest);

        // 5. CLEAR the cart so it's empty for the next session
        cartRepo.deleteAll();

        return savedOrder;
    }
}