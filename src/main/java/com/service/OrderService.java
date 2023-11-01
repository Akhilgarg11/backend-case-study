package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Cart;
import com.entity.CartItem;
import com.entity.OrderItem;
import com.entity.ProductDetails;
import com.entity.UserDetails;
import com.entity.UserOrder;
import com.repository.CartItemRepository;
import com.repository.CartRepository;
import com.repository.OrderItemRepository;
import com.repository.OrderRepository;
import com.repository.ProductRepository;
import com.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private OrderItemRepository orderItemRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private CartItemRepository cartItemRepo;

	@Transactional
	public UserOrder createOrder(int userId) {

		UserDetails user;

		Optional<UserDetails> getUser = userRepo.findById(userId);
		if (getUser.isPresent()) {
			user = (UserDetails) getUser.get();
		} else {
			throw new EntityNotFoundException("User Not Exist");
		}

		Cart cart = userRepo.findCartByUserId(userId);
		int cartId = cart.getCartID();

		Optional<List<CartItem>> getCartItems = cartRepo.getAllcartItem(cartId);
		List<CartItem> cartItems = getCartItems.get();

		if (cartItems.size() == 0) {
			System.out.println("hfjdks");
			throw new EntityNotFoundException("Cart is Empty");

		}

		else {
			System.out.println("aaaaa");
			
			List<OrderItem> listOrderItems = new ArrayList<OrderItem>();

			UserOrder userOrder = new UserOrder();
			orderRepo.save(userOrder);

			for (CartItem i : cartItems) {
				OrderItem orderItem = new OrderItem();
				orderItem.setProduct(i.getProduct());
				orderItem.setQuantity(i.getQuantity());
				orderItem.setUserOrder(userOrder);
				orderItemRepo.save(orderItem);
				listOrderItems.add(orderItem);
			}
			userOrder.setUser(user);
			userOrder.setOrderItems(listOrderItems);

			cartItemRepo.removeFromCart(cart);
			cartRepo.save(cart);
			userRepo.save(user);
			orderRepo.save(userOrder);
			return userOrder;
		}

	}

	public List<UserOrder> getOrderHistory(int userId) {

		UserDetails user;

		Optional<UserDetails> getUser = userRepo.findById(userId);
		if (getUser.isPresent()) {
			user = (UserDetails) getUser.get();
		} else {
			throw new EntityNotFoundException("User Not Exist");
		}

		return user.getUserOrders();
	}

	public UserOrder buyNow(int userId, int productId, int quantity) {

		UserDetails user;

		Optional<UserDetails> getUser = userRepo.findById(userId);
		if (getUser.isPresent()) {
			user = (UserDetails) getUser.get();
		} else {
			throw new EntityNotFoundException("User Not Exist");
		}

		Optional<ProductDetails> optional = productRepo.findById(productId);
		ProductDetails product = (ProductDetails) optional.get();

		if (product == null)
			throw new EntityNotFoundException("Product not found");

		UserOrder userOrder = new UserOrder();
		orderRepo.save(userOrder);
		
		List<OrderItem> listOrderItems = new ArrayList<OrderItem>();

		OrderItem orderItem = new OrderItem();

		orderItem.setProduct(product);
		orderItem.setQuantity(quantity);
		orderItem.setUserOrder(userOrder);

		orderItemRepo.save(orderItem);
		listOrderItems.add(orderItem);

		userOrder.setUser(user);
		userOrder.setOrderItems(listOrderItems);

		userRepo.save(user);
		orderRepo.save(userOrder);
		return userOrder;
	}

}
