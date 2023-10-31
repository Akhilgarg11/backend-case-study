package com.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_order")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	@ManyToOne
	private UserDetails user;
	
	private String orderStatus;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderItem> orderItems;
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(int orderId, String orderStatus, List<OrderItem> orderItems) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.orderItems = orderItems;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	} 
	
	
	
}
