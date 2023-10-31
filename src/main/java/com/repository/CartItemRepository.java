package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Cart;
import com.entity.CartItem;
import com.entity.ProductDetails;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	@Query("SELECT c from CartItem c WHERE c.cart= :cart AND c.product = :product")
	Optional<CartItem> findByCartAndProduct(@Param("cart")Cart cart, @Param("product") ProductDetails product);
	
	@Query("SELECT c FROM CartItem c WHERE c.cart = :cart AND c.cartItemId = :id")
	Optional<CartItem> findByCartAndId(@Param("cart")Cart cart, @Param("id") int itemId );
	
	@Modifying
	@Query("DELETE FROM CartItem c WHERE c.cart = :cart AND c.product = :product")
	void deleteByCartAndProduct(@Param("cart") Cart cart, @Param("product") ProductDetails product);

}
