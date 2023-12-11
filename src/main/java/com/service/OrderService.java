// Import necessary classes and interfaces
package com.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.SelectedCartItem;
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

// Declare the class as a Spring service
@Service
public class OrderService {

	// Autowire repositories for database operations related to orders, items, products, users, and carts
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

	 /**
     * Creates an order for the user with the specified user ID and selected cart items.
     *
     * @param userId              The ID of the user placing the order.
     * @param selectedCartItems   List of selected cart items for the order.
     * @return                    The created UserOrder.
     * @throws EntityNotFoundException If the user or cart is not found.
     */
	@Transactional
	public UserOrder createOrder(int userId, ArrayList<SelectedCartItem> selectedCartItems) {

		// Retrieve user details based on the provided user ID
		UserDetails user;
		LocalDate currentDate = LocalDate.now();
		LocalDate dateAfter5Days = currentDate.plusDays(5);
		Optional<UserDetails> getUser = userRepo.findById(userId);
		if (getUser.isPresent()) {
			user = getUser.get();
		} else {
			// Throw an exception if the user is not found
			throw new EntityNotFoundException("User Not Exist");
		}

		// Retrieve the user's cart
		Cart cart = userRepo.findCartByUserId(userId);
		int cartId = cart.getCartID();

		// Create a list to store order items
		List<OrderItem> listOrderItems = new ArrayList<>();

		// Create a new UserOrder and save it in the database
		UserOrder userOrder = new UserOrder();
		orderRepo.save(userOrder);

		// Iterate through selected cart items, create order items, and save them in the database
		for (SelectedCartItem i : selectedCartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(i.getProduct());
			orderItem.setQuantity(i.getQuantity());
			orderItem.setUserOrder(userOrder);
			orderItemRepo.save(orderItem);
			listOrderItems.add(orderItem);
		}

		// Set user, order items, order status, delivery date, and order date for the user order
		userOrder.setUser(user);
		userOrder.setOrderItems(listOrderItems);
		userOrder.setOrderStatus("Your Order has been Accepted!");
		userOrder.setDeliveryDate(dateAfter5Days);
		userOrder.setOrderDate(currentDate);

		// Remove selected items from the user's cart
		for (SelectedCartItem i : selectedCartItems) {
			cartItemRepo.deleteByCartAndProduct(cart, i.getProduct());
		}

		// Save changes in the cart, user, and order in the database
		cartRepo.save(cart);
		userRepo.save(user);
		orderRepo.save(userOrder);

		// Return the created user order
		return userOrder;
	}

	/**
     * Retrieves the order history for a user with the specified user ID.
     *
     * @param userId The ID of the user.
     * @return List of UserOrder representing the order history.
     * @throws EntityNotFoundException If the user is not found.
     */
	public List<UserOrder> getOrderHistory(int userId) {

		// Retrieve user details based on the provided user ID
		UserDetails user;

		Optional<UserDetails> getUser = userRepo.findById(userId);
		if (getUser.isPresent()) {
			user = getUser.get();
		} else {
			// Throw an exception if the user is not found
			throw new EntityNotFoundException("User Not Exist");
		}

		// Return the order history for the user
		return user.getUserOrders();
	}

	  /**
     * Creates an order for the user with the specified user ID and product ID.
     *
     * @param userId    The ID of the user placing the order.
     * @param productId The ID of the product to be ordered.
     * @param quantity  The quantity of the product to be ordered.
     * @return          The created UserOrder.
     * @throws EntityNotFoundException If the user or product is not found.
     */
	public UserOrder buyNow(int userId, int productId, int quantity) {
		
		// Retrieve current date and date after 5 days
		LocalDate currentDate = LocalDate.now();
		LocalDate dateAfter5Days = currentDate.plusDays(5);
		
		// Retrieve user details based on the provided user ID
		UserDetails user;
		Optional<UserDetails> getUser = userRepo.findById(userId);
		if (getUser.isPresent()) {
			user = getUser.get();
		} else {
			// Throw an exception if the user is not found
			throw new EntityNotFoundException("User Not Exist");
		}

		// Retrieve the product details based on the provided product ID
		Optional<ProductDetails> optional = productRepo.findById(productId);
		ProductDetails product = optional.orElseThrow(() -> new EntityNotFoundException("Product not found"));

		// Create a new UserOrder and save it in the database
		UserOrder userOrder = new UserOrder();
		orderRepo.save(userOrder);

		// Create a list to store order items
		List<OrderItem> listOrderItems = new ArrayList<>();

		// Create a new OrderItem, set its details, and save it in the database
		OrderItem orderItem = new OrderItem();
		orderItem.setProduct(product);
		orderItem.setQuantity(quantity);
		orderItem.setUserOrder(userOrder);
		orderItemRepo.save(orderItem);
		listOrderItems.add(orderItem);

		// Set user, order items, order status, delivery date, and order date for the user order
		userOrder.setUser(user);
		userOrder.setOrderItems(listOrderItems);
		userOrder.setOrderStatus("Your Order has been Accepted!");
		userOrder.setDeliveryDate(dateAfter5Days);
		userOrder.setOrderDate(currentDate);

		// Save changes in the user and order in the database
		userRepo.save(user);
		orderRepo.save(userOrder);

		// Return the created user order
		return userOrder;
	}
}

