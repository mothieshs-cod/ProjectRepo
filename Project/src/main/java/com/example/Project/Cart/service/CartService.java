package com.example.Project.Cart.service;

import com.example.Project.Cart.model.Cart;
import com.example.Project.Cart.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class CartService {

    @Autowired
    CartRepo repo;

    // GET ALL
    public List<Cart> getcart() {
        return repo.findAll();
    }

    // ADD / CREATE
    public Cart addCart(Cart cart) {
        // Calculate total cost before saving
        cart.setTotCost(cart.getListPrice() * cart.getQuantity());
        return repo.save(cart);
    }

    // UPDATE based on Item ID
    public Cart updateCart(int itemId, Cart details) {
        Cart existingCart = repo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Update fields using Lombok setters
        existingCart.setProductId(details.getProductId());
        existingCart.setDescription(details.getDescription());
        existingCart.setInStock(details.isInStock());
        existingCart.setQuantity(details.getQuantity());
        existingCart.setListPrice(details.getListPrice());

        // Recalculate total cost
        existingCart.setTotCost(existingCart.getListPrice() * existingCart.getQuantity());

        return repo.save(existingCart);
    }

    // DELETE
    public String deleteCart(int itemId) {
        repo.deleteById(itemId);
        return "Item " + itemId + " deleted successfully";
    }
}
