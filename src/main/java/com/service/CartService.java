package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Cart;
import com.entity.CartItem;
import com.entity.ProductDetails;
import com.entity.UserDetails;
import com.repository.CartItemRepository;
import com.repository.CartRepository;
import com.repository.ProductRepository;
import com.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private CartItemRepository itemRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private UserRepository userRepo;

	@Transactional
	public CartItem addToCart(int userId, int productId, int quantity) {

		UserDetails user;

		Optional<UserDetails> getUser = userRepo.findById(userId);
		if (getUser.isPresent()) {
			user = (UserDetails) getUser.get();
		} else {
			throw new EntityNotFoundException("User Not Exist");
		}

		Cart cart = userRepo.findCartByUserId(userId);

		if (cart == null) {
			cart = new Cart();
			cart.setUser(user);
			cartRepo.save(cart);
		}

		Optional<ProductDetails> optional = productRepo.findById(productId);
		ProductDetails product = (ProductDetails) optional.get();

		if (product == null)
			throw new EntityNotFoundException("Product not found");

		Optional<CartItem> existingCartItem = itemRepo.findByCartAndProduct(cart, product);
		CartItem cartItem;
		if (existingCartItem.isPresent()) {
			cartItem = existingCartItem.get();
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		} else {

			cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(quantity);

		}
		itemRepo.save(cartItem);
		user.setCart(cart);
		userRepo.save(user);
		cartRepo.save(cart);

		return cartItem;
	}

	public Cart getCart(int userId) {

		Cart cart = userRepo.findCartByUserId(userId);

		return cart;
	}

	public CartItem getCartItem(int userId, int itemId) {
		Cart cart = userRepo.findCartByUserId(userId);
		Optional<CartItem> getItem = itemRepo.findByCartAndId(cart, itemId);
		CartItem item = getItem.get();

		return item;
	}

	@Transactional
	public ProductDetails removeFromCart(int userId, int productId) {
		Cart cart = userRepo.findCartByUserId(userId);

		Optional<ProductDetails> optional = productRepo.findById(productId);

		if (!optional.isPresent()) {
			throw new EntityNotFoundException("Product not found");
		}

		ProductDetails product = (ProductDetails) optional.get();

		itemRepo.deleteByCartAndProduct(cart, product);

		return product;
	}

	@Transactional
	public Cart changeQuantityByItemId(int itemId, int quantity) {
		Optional<CartItem> itemOptional = itemRepo.findById(itemId);
		CartItem item = itemOptional.get();
		
		if(quantity <= 0) {
			itemRepo.deleteById(itemId);
		}

		else item.setQuantity(quantity);

		Cart cart = item.getCart();
		return cart;

	}

	@Transactional
	public Cart changeQuantityByUserIdAndProductId(int userId, int productId, int quantity) {

		Cart cart = userRepo.findCartByUserId(userId);
		Optional<ProductDetails> optional = productRepo.findById(productId);
		ProductDetails product = (ProductDetails) optional.get();

		Optional<CartItem> itemOptional = itemRepo.findByCartAndProduct(cart, product);
		CartItem item = itemOptional.get();
		
		if(quantity <= 0) {
			itemRepo.deleteByCartAndProduct(cart, product);
		}

		else item.setQuantity(quantity);

		return cart;
	}

}
