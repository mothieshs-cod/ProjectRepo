package com.example.Project.Cart.controller;

import com.example.Project.Cart.model.Cart;
import java.util.*;
import com.example.Project.Cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable int userId) {
        return service.getCart(userId);
    }

    @PostMapping("/{userId}/add")
    public Cart addToCart(@PathVariable int userId,
                          @RequestParam String variantId,
                          @RequestParam int quantity) {

        return service.addToCart(userId, variantId, quantity);
    }

    @DeleteMapping("/{userId}/item/{cartItemId}")
    public Cart removeItem(@PathVariable int userId,
                           @PathVariable Long cartItemId) {

        return service.removeItem(userId, cartItemId);
    }

    @PutMapping("/{userId}/item/{cartItemId}")
    public Cart updateQuantity(@PathVariable int userId,
                               @PathVariable Long cartItemId,
                               @RequestParam int quantity) {

        return service.updateQuantity(userId, cartItemId, quantity);
    }
}