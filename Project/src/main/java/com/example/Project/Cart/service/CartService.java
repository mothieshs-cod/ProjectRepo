package com.example.Project.Cart.service;

import com.example.Project.Cart.model.Cart;
import com.example.Project.Cart.model.CartItem;
import com.example.Project.Cart.repository.CartRepository;
import com.example.Project.Categories.model.ProductVariant;
import com.example.Project.Categories.repository.ProductVariantRepository;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductVariantRepository variantRepository;

    public CartService(CartRepository cartRepository,
                       ProductVariantRepository variantRepository) {
        this.cartRepository = cartRepository;
        this.variantRepository = variantRepository;
    }

    public Cart getOrCreateCart(int userId) {

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .userId(userId)
                                .items(new ArrayList<>())
                                .build()
                ));
    }

    public Cart addToCart(int userId, String variantId, int quantity) {

        Cart cart = getOrCreateCart(userId);

        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductVariant().getVariantId().equals(variantId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem item = CartItem.builder()
                    .cart(cart)
                    .productVariant(variant)
                    .quantity(quantity)
                    .build();

            cart.getItems().add(item);
        }

        return cartRepository.save(cart);
    }

    public Cart removeItem(int userId, Long cartItemId) {

        Cart cart = getOrCreateCart(userId);

        cart.getItems().removeIf(item -> item.getCartItemId().equals(cartItemId));

        return cartRepository.save(cart);
    }

    public Cart updateQuantity(int userId, Long cartItemId, int quantity) {

        Cart cart = getOrCreateCart(userId);

        cart.getItems().forEach(item -> {
            if (item.getCartItemId().equals(cartItemId)) {
                item.setQuantity(quantity);
            }
        });

        return cartRepository.save(cart);
    }

    public Cart getCart(int userId) {
        return getOrCreateCart(userId);
    }
}