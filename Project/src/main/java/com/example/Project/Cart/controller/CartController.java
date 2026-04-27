package com.example.Project.Cart.controller;
import com.example.Project.Cart.model.Cart;
import java.util.*;
import com.example.Project.Cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart") // Good practice to version/path your API
public class CartController {

    @Autowired
    CartService service;

    @GetMapping("/all")
    public List<Cart> getCart() {
        return service.getcart();
    }

    @PostMapping("/add")
    public Cart addToCart(@RequestBody Cart cart) {
        return service.addCart(cart);
    }

    @PutMapping("/update/{id}")
    public Cart updateCart(@PathVariable int id, @RequestBody Cart cart) {
        return service.updateCart(id, cart);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteFromCart(@PathVariable int id) {
        return service.deleteCart(id);
    }
}


