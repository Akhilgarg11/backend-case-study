package com.dto;

import com.entity.ImageModel;

public class GetProductResponse {
	private int productId;
	private String name;
	private String price;
	private String details;
	private String category;
	private String brand;	
	private ImageModel productImage;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
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
	public GetProductResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GetProductResponse(int productId, String name, String price, String details, String category, String brand,
			ImageModel productImage) {
		super();
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.details = details;
		this.category = category;
		this.brand = brand;
		this.productImage = productImage;
	}
	
	
}
