package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.AddProductRequest;
import com.dto.Filterdto;
import com.dto.GetProductResponse;
import com.dto.UpdateProductRequest;
import com.entity.ProductDetails;
import com.entity.UserDetails;
import com.repository.ProductRepository;
import com.repository.UserRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private int MAXPRICE = 0;

	public ProductDetails addProduct(AddProductRequest addProduct, int sellerId) {

		ProductDetails product = new ProductDetails();

		product.setBrand(addProduct.getBrand());
		product.setCategory(addProduct.getCategory());
		product.setName(addProduct.getName());
		product.setDetails(addProduct.getDetails());
		product.setPrice(addProduct.getPrice());
		product.setProductImage(addProduct.getProductImage());
		
		Optional<UserDetails> sellerOptional = userRepository.findById(sellerId); 
		UserDetails seller = sellerOptional.get();
		
		product.setSeller(seller);
		MAXPRICE = Math.max(MAXPRICE, Integer.parseInt(addProduct.getPrice()));
		productRepository.save(product);

		return product;
	}
	
	public List<GetProductResponse> getAllProducts(){
		
		Optional<List<ProductDetails>> optional = productRepository.getAllProducts();
		
		List<GetProductResponse> list = new ArrayList<GetProductResponse>();
		
		if(optional.isPresent()) {
			List<ProductDetails> p = optional.get();
			
			for(int i=0; i<p.size(); i++) {
				GetProductResponse resp = new GetProductResponse();
				resp.setBrand(p.get(i).getBrand());
				resp.setCategory(p.get(i).getCategory());
				resp.setDetails(p.get(i).getDetails());
				resp.setName(p.get(i).getName());
				resp.setPrice(p.get(i).getPrice());
				resp.setProductImage(p.get(i).getProductImage());
				resp.setProductId(p.get(i).getProductID());
				
				list.add(resp);
			}
			
			return list;
		}
		
		else return null;
		
	}
	
	public List<GetProductResponse> getAllProductsPaginated(int page, int size) {
	    PageRequest pageable = PageRequest.of(page, size);
	    System.out.println(page);
	    System.out.println(size);
	    Optional<List<ProductDetails>> optional = productRepository.getAllProductsPaginated(pageable);

	    List<GetProductResponse> list = new ArrayList<GetProductResponse>();
	    if (optional.isPresent()) {
	        List<ProductDetails> p = optional.get();
	        

	        for (ProductDetails product : p) {
	            GetProductResponse resp = new GetProductResponse();
	            resp.setBrand(product.getBrand());
	            resp.setCategory(product.getCategory());
	            resp.setDetails(product.getDetails());
	            resp.setName(product.getName());
	            resp.setPrice(product.getPrice());
	            resp.setProductImage(product.getProductImage());
	            resp.setProductId(product.getProductID());
	            list.add(resp);
	        }

	    }
        return  list;

	}



	public ProductDetails getProduct(int id) {

		Optional<ProductDetails> optional = productRepository.findById(id);
		ProductDetails product = (ProductDetails) optional.get();

		return product;

	}
	
	@Transactional
	public void deleteProductById(int productId) {
		productRepository.deleteProductById(productId);
	}
	
	public List<ProductDetails> getProductsBySellerId(int sellerId) {
		
		Optional<UserDetails> sellerOptional = userRepository.findById(sellerId); 
		UserDetails seller = sellerOptional.get();
		
		List<ProductDetails> list = productRepository.getProductsBySeller(seller);

		return list;
	}

	public ProductDetails updateProduct(UpdateProductRequest updateProduct, int productId) {

		Optional<ProductDetails> optional = productRepository.findById(productId);
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

		if (brand == null || brand.size()== 0)
			brand = productRepository.getAllBrands();

		if (category == null || category.size() == 0)
			category = productRepository.getAllCategory();

		List<ProductDetails> list = productRepository.getFilteredProducts(minPrice, maxPrice, brand, category);
		
		System.out.println(maxPrice);
        System.out.println(minPrice);
		
		return list;
	}

	
	
	
	
	
	
}
