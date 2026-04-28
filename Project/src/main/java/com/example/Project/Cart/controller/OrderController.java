package com.example.Project.Cart.controller;

import com.example.Project.Cart.model.Order;
import com.example.Project.Cart.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/checkout/{userId}")
    public Order checkout(@PathVariable int userId, @RequestBody Order orderRequest) {
        return service.placeOrder(userId, orderRequest);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Integer userId) {
        return service.getOrdersByUser(userId);
    }
}