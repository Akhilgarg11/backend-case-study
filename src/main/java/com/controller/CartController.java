package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Cart;
import com.entity.CartItem;
import com.entity.ProductDetails;
import com.generic.GenericResponse;
import com.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping("/{userId}/add/{productId}/{quantity}")
	public ResponseEntity<?> addToCart(@PathVariable("userId") int userId, @PathVariable("productId") int productId,
			@PathVariable("quantity") int quantity) {
		CartItem cartItem;
		try {
			cartItem = this.cartService.addToCart(userId, productId, quantity);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(cartItem, HttpStatus.OK);
	}

	@GetMapping("/{id}/getCart")
	public ResponseEntity<?> getcartByUserId(@PathVariable("id") int userId) {
		Cart cart;
		try {
			cart = this.cartService.getCart(userId);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@GetMapping("/{userId}/getCartItem/{itemId}")
	public ResponseEntity<?> getCartItemByUserIdAndProductId(@PathVariable("userId") int userId,
			@PathVariable("itemId") int itemId) {
		CartItem item;
		try {
			item = this.cartService.getCartItem(userId, itemId);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new GenericResponse<>(item, "Cart Item Fetched Succesfully!", true), HttpStatus.OK);
	}
	
	@GetMapping("/{userId}/getCartItemByProduct/{productId}")
	public ResponseEntity<?> getcartItemByUserIdAndproductId(@PathVariable("userId") int userId,
			@PathVariable("productId") int productId) {
		CartItem item;
		try {
			item = this.cartService.getCartItemByUserIdAndProductId(userId, productId);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(item, HttpStatus.OK);
	}

	@GetMapping("/{userId}/remove/{productId}")
	public ResponseEntity<?> removeFromCart(@PathVariable("userId") int userId,
			@PathVariable("productId") int productId) {
		ProductDetails product;
		try {
			product = this.cartService.removeFromCart(userId, productId);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new GenericResponse<>(product, "Product Removed Succesfully!", true),
				HttpStatus.OK);
	}
	
	@PostMapping("changeQuantity/{cartItemId}")
	public ResponseEntity<?> changeQuantityByItemId(@RequestBody int quantity, @PathVariable("cartItemId") int cartItemId) {
		Cart cart;
		try {
			cart = this.cartService.changeQuantityByItemId(cartItemId, quantity);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new GenericResponse<>(cart, "Quantity Changed Succesfully!", true),
				HttpStatus.OK);
	}
	
	
	@PostMapping("{userId}/changeQuantity/{productId}")
	public ResponseEntity<?> changeQuantityByUserIdAndProductId(@RequestBody int quantity, @PathVariable("userId") int userId,
			@PathVariable("productId") int productId) {
		Cart cart;
		try {
			cart = this.cartService.changeQuantityByUserIdAndProductId(userId, productId, quantity);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new GenericResponse<>(cart, "Quantity Changed Succesfully!", true),
				HttpStatus.OK);
	}
	
	
	

}
