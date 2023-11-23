package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.SelectedCartItem;
import com.entity.UserOrder;
import com.generic.GenericResponse;
import com.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/{userId}/createOrder")
	public ResponseEntity<?> createOrder(@PathVariable("userId") int userId, @RequestBody ArrayList<SelectedCartItem> selectedCartItems) {
		UserOrder userOrder;
		try {
			userOrder = this.orderService.createOrder(userId, selectedCartItems);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>( userOrder, HttpStatus.OK);
	}

	@GetMapping("/{userId}/getOrders")
	public ResponseEntity<?> getOrderHistory(@PathVariable("userId") int userId) {
		List<UserOrder> userOrders;
		try {
			userOrders = this.orderService.getOrderHistory(userId);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(userOrders, HttpStatus.OK);
	}

	@GetMapping("/{userId}/buyNow/{productId}/{quantity}")
	public ResponseEntity<?> buyNow(@PathVariable("userId") int userId, @PathVariable("productId") int productId,
			@PathVariable("quantity") int quantity) {
		UserOrder userOrder;
		try {
			userOrder = this.orderService.buyNow(userId, productId, quantity);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new GenericResponse<>(userOrder, "Order Placed Succesfully!", true), HttpStatus.OK);
	}

}
