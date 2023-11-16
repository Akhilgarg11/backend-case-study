package com.entity;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="product_details")
public class ProductDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productID;
	private String name;
	private String details;
	private String price;
	private String category;
	private String brand;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "seller_id")
	private UserDetails seller;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private ImageModel productImage;
	
	@JsonIgnore
	@OneToMany(mappedBy="product")
	private List<CartItem> cartItems;
	
	@JsonIgnore
	@OneToMany(mappedBy="product")
	private List<OrderItem> orderItems;
	
	public ProductDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public UserDetails getSeller() {
		return seller;
	}



	public void setSeller(UserDetails seller) {
		this.seller = seller;
	}



	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}


	public ImageModel getProductImage() {
		return productImage;
	}


	public void setProductImage(ImageModel productImage) {
		this.productImage = productImage;
	}


	public List<CartItem> getCartItems() {
		return cartItems;
	}


	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}


	public List<OrderItem> getOrderItems() {
		return orderItems;
	}


	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
}
