package com.example.Project.Cart.service;

import com.example.Project.Cart.model.Cart;
import com.example.Project.Cart.model.CartItem;
import com.example.Project.Cart.model.Order;
import com.example.Project.Cart.model.OrderItem;
import com.example.Project.Cart.repository.CartRepository;
import com.example.Project.Cart.repository.OrderRepo;
import com.example.Project.Categories.model.ProductVariant;
import com.example.Project.Categories.repository.ProductVariantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepo orderRepo;
    private final ProductVariantRepository variantRepo;
    private final CartService cartService;

    public OrderService(
            CartRepository cartRepository,
            OrderRepo orderRepo,
            ProductVariantRepository variantRepo,
            CartService cartService) {

        this.cartRepository = cartRepository;
        this.orderRepo = orderRepo;
        this.variantRepo = variantRepo;
        this.cartService = cartService;
    }


    public List<Order> getOrdersByUser(Integer userId) {
        return orderRepo.findByUserId(userId);
    }

    public Order placeOrder(int userId, Order orderRequest) {

        Cart cart = cartService.getCart(userId);

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem item : cart.getItems()) {

            ProductVariant variant = item.getProductVariant();

            if (variant.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException(
                        "Out of stock for variant: " + variant.getVariantId()
                );
            }

            variant.setStockQuantity(
                    variant.getStockQuantity() - item.getQuantity()
            );
            variantRepo.save(variant);

            double price = variant.getPrice();

            OrderItem orderItem = OrderItem.builder()
                    .variantId(variant.getVariantId())
                    .quantity(item.getQuantity())
                    .price(price)
                    .build();

            total += price * item.getQuantity();
            orderItems.add(orderItem);
        }

        Order order = Order.builder()
                .userId(userId)
                .totalAmount(total)
                .status("Processing")
                .createdAt(LocalDateTime.now())
                .fullName(orderRequest.getFullName())
                .address(orderRequest.getAddress())
                .mobileNumber(orderRequest.getMobileNumber())
                .city(orderRequest.getCity())
                .pincode(orderRequest.getPincode())
                .state(orderRequest.getState())
                .country(orderRequest.getCountry())
                .cardType(orderRequest.getCardType())
                .cardNumber(orderRequest.getCardNumber())
                .expiryDate(orderRequest.getExpiryDate())
                .items(orderItems)
                .build();

        orderItems.forEach(item -> item.setOrder(order));

        cart.getItems().clear();
        cartRepository.save(cart);

        return orderRepo.save(order);
    }

    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        order.setStatus(status);
        return orderRepo.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
}