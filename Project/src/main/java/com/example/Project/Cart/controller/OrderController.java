package com.example.Project.Cart.controller;

import com.example.Project.Cart.model.Order;
import com.example.Project.Cart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public Order checkout(@RequestBody Order orderRequest) {
        return orderService.processCheckout(orderRequest);
    }
}