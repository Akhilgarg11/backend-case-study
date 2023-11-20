package com.dto;

import java.util.List;

import com.entity.Cart;
import com.entity.UserAddress;
import com.entity.UserOrder;

public class UserResponse {
	private String name;
	private String email;
	private String phone;
	private List<UserOrder> userOrders;
    private	Cart cart;
    private UserAddress address;
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<UserOrder> getUserOrders() {
		return userOrders;
	}
	public void setUserOrders(List<UserOrder> userOrders) {
		this.userOrders = userOrders;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public UserAddress getAddress() {
		return address;
	}
	public void setAddress(UserAddress address) {
		this.address = address;
	}
	public UserResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
    
    
}
