package com.example.Project.Cart.repository;

import com.example.Project.Cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {
}
