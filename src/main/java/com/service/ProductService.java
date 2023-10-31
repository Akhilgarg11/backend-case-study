package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.AddProductRequest;
import com.dto.Filterdto;
import com.entity.ProductDetails;
import com.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	private int MAXPRICE = 0;

	public ProductDetails addProduct(AddProductRequest addProduct) {

		ProductDetails product = new ProductDetails();

		product.setBrand(addProduct.getBrand());
		product.setCategory(addProduct.getCategory());
		product.setName(addProduct.getName());
		product.setDetails(addProduct.getDetails());
		product.setPrice(addProduct.getPrice());
		MAXPRICE = Math.max(MAXPRICE, Integer.parseInt(addProduct.getPrice()));
		productRepository.save(product);

		return product;
	}

	public ProductDetails getProduct(int id) {

		Optional<ProductDetails> optional = productRepository.findById(id);
		ProductDetails product = (ProductDetails) optional.get();

		return product;

	}

	public ProductDetails updateProduct(ProductDetails updateProduct) {

		int id = updateProduct.getProductID();
		Optional<ProductDetails> optional = productRepository.findById(id);
		ProductDetails product = (ProductDetails) optional.get();

		product.setBrand(updateProduct.getBrand());
		product.setCategory(updateProduct.getCategory());
		product.setName(updateProduct.getName());
		product.setDetails(updateProduct.getDetails());
		product.setPrice(updateProduct.getPrice());
		MAXPRICE = Math.max(MAXPRICE, Integer.parseInt(updateProduct.getPrice()));
		productRepository.save(product);

		return product;

	}

	public List<ProductDetails> getProductsByCategory(String category) {

		List<ProductDetails> list = productRepository.getProductsByCategory(category);

		return list;
	}

	public List<ProductDetails> getProductsByString(String str) {

		List<ProductDetails> list = productRepository.getProductsByString(str);

		return list;
	}

	public List<ProductDetails> getFilteredProducts(Filterdto filterObj) {

		int minPrice;
		if (filterObj.getMinPrice() == null)
			minPrice = 0;
		else
			minPrice = Integer.parseInt(filterObj.getMinPrice());

		int maxPrice;
		if (filterObj.getMaxPrice() == null)
			maxPrice = Integer.MAX_VALUE;
		else
			maxPrice = Integer.parseInt(filterObj.getMaxPrice());

		List<String> brand = filterObj.getBrand();
		List<String> category = filterObj.getCategory();

		if (brand == null)
			brand = productRepository.getAllBrands();

		if (category == null)
			category = productRepository.getAllCategory();

		List<ProductDetails> list = productRepository.getFilteredProducts(minPrice, maxPrice, brand, category);
		
		System.out.println(maxPrice);
        System.out.println(minPrice);
		
		return list;
	}

	
	
	
	
	
	
}
