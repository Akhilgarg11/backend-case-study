package com.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartID;
	
	@OneToMany(mappedBy="cart",cascade = CascadeType.ALL)
	private List<CartItem> cartItems;
	
	@OneToOne(mappedBy="cart")
	private UserDetails user;
	
	
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(int cartID, List<CartItem> cartItems, UserDetails user) {
		super();
		this.cartID = cartID;
		this.cartItems = cartItems;
		this.user = user;
	}

	public int getCartID() {
		return cartID;
	}

	public void setCartID(int cartID) {
		this.cartID = cartID;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public UserDetails getUser() {
		return user;
	}

	public void setUser(UserDetails user) {
		this.user = user;
	}

	
}
