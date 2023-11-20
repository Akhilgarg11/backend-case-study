package com.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name="user_details")
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userID;

	private String name;
	
	@Column(unique=true)
	private String email;
	
	private String password;
	private String phone;
	
	String role;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private Cart cart;
	
	@OneToMany(mappedBy="user",cascade = CascadeType.ALL)
	private List<UserOrder> userOrders;
	
	@OneToOne(cascade = CascadeType.ALL)
	private UserAddress address;
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public UserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDetails(int userID, String name, String email, String password, String phone, UserAddress address) {
		super();
		this.userID = userID;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
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

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public UserAddress getAddress() {
		return address;
	}

	public void setAddress(UserAddress address) {
		this.address = address;
	}
	
}
