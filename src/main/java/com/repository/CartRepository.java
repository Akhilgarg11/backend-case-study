package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Cart;
import com.entity.CartItem;
import com.entity.UserDetails;


public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	@Query("SELECT c.cartItems FROM Cart c WHERE c.cartID = :id")
	Optional<List<CartItem>> getAllcartItem(@Param("id") int cartId);

}
