package com.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_order")
public class UserOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	
	@JsonIgnore
	@ManyToOne
	private UserDetails user;
	
	private String orderStatus;
	
	@OneToMany(mappedBy= "userOrder",cascade = CascadeType.ALL)
	private List<OrderItem> orderItems;

	public UserOrder(int orderId, UserDetails user, String orderStatus, List<OrderItem> orderItems) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.orderStatus = orderStatus;
		this.orderItems = orderItems;
	}

	public UserOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
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
