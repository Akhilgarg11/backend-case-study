package com.dto;

import com.entity.ProductDetails;

public class SelectedCartItem {
	ProductDetails product;
	int quantity;
	public ProductDetails getProduct() {
		return product;
	}
	public void setProduct(ProductDetails product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public SelectedCartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
