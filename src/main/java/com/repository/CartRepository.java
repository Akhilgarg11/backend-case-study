package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Cart;
import com.entity.CartItem;
import com.entity.UserDetails;

public interface CartRepository extends JpaRepository<Cart, Integer> {


}
