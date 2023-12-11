// Import necessary classes and interfaces
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

// Declare the class as a Spring service
@Service
public class ProductService {

	// Autowire ProductRepository for database operations related to products
	@Autowired
	private ProductRepository productRepository;
	
	// Autowire UserRepository for database operations related to users
	@Autowired
	private UserRepository userRepository;
	
	// Set the maximum price to zero initially
	private int MAXPRICE = 0;

	/**
     * Adds a new product to the system.
     * 
     * @param addProduct The request containing product details.
     * @param sellerId   The ID of the seller adding the product.
     * @return The created ProductDetails.
     */
	public ProductDetails addProduct(AddProductRequest addProduct, int sellerId) {

		// Create a new ProductDetails object and set its properties
		ProductDetails product = new ProductDetails();
		product.setBrand(addProduct.getBrand());
		product.setCategory(addProduct.getCategory());
		product.setName(addProduct.getName());
		product.setDetails(addProduct.getDetails());
		product.setPrice(addProduct.getPrice());
		product.setProductImage(addProduct.getProductImage());
		
		// Retrieve the seller details from the database based on the provided seller ID
		Optional<UserDetails> sellerOptional = userRepository.findById(sellerId); 
		UserDetails seller = sellerOptional.get();
		
		// Set the seller for the product
		product.setSeller(seller);
		
		// Update the maximum price if the current product's price is greater
		MAXPRICE = Math.max(MAXPRICE, Integer.parseInt(addProduct.getPrice()));
		
		// Save the new product in the database
		productRepository.save(product);

		// Return the created product details
		return product;
	}
	
	  /**
     * Retrieves all products from the system.
     * 
     * @return List of GetProductResponse containing product details.
     */
	public List<GetProductResponse> getAllProducts(){
		
		// Retrieve all products from the database
		Optional<List<ProductDetails>> optional = productRepository.getAllProducts();
		
		// Create a list to store GetProductResponse objects
		List<GetProductResponse> list = new ArrayList<GetProductResponse>();
		
		// If products are present, map them to GetProductResponse and add to the list
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
			
			// Return the list of product details
			return list;
		}
		
		else 
			// Return null if no products are present
			return null;
		
	}
	
	/**
     * Retrieves a paginated list of products from the system.
     * 
     * @param page The page number.
     * @param size The number of products per page.
     * @return List of GetProductResponse containing paginated product details.
     */
	public List<GetProductResponse> getAllProductsPaginated(int page, int size) {
	    // Create a PageRequest for pagination
	    PageRequest pageable = PageRequest.of(page, size);
	    
	    // Retrieve paginated products from the database
	    Optional<List<ProductDetails>> optional = productRepository.getAllProductsPaginated(pageable);

	    // Create a list to store GetProductResponse objects
	    List<GetProductResponse> list = new ArrayList<GetProductResponse>();
	    if (optional.isPresent()) {
	        List<ProductDetails> p = optional.get();
	        
	        // Map paginated products to GetProductResponse and add to the list
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


	/**
     * Retrieves a specific product by ID.
     * 
     * @param id The ID of the product to retrieve.
     * @return The retrieved ProductDetails.
     */
	public ProductDetails getProduct(int id) {

		// Retrieve product details from the database based on the provided product ID
		Optional<ProductDetails> optional = productRepository.findById(id);
		ProductDetails product = (ProductDetails) optional.get();

		// Return the retrieved product details
		return product;

	}
	
	/**
     * Deletes a product by ID.
     * 
     * @param productId The ID of the product to delete.
     */
	@Transactional
	public void deleteProductById(int productId) {
		// Delete product from the database based on the provided product ID
		productRepository.deleteProductById(productId);
	}
	
	 /**
     * Retrieves products associated with a specific seller.
     * 
     * @param sellerId The ID of the seller.
     * @return List of ProductDetails associated with the seller.
     */
	public List<ProductDetails> getProductsBySellerId(int sellerId) {
		
		// Retrieve seller details from the database based on the provided seller ID
		Optional<UserDetails> sellerOptional = userRepository.findById(sellerId); 
		UserDetails seller = sellerOptional.get();
		
		// Retrieve products associated with the seller from the database
		List<ProductDetails> list = productRepository.getProductsBySeller(seller);

		// Return the list of products associated with the seller
		return list;
	}

	/**
     * Updates a product's details.
     * 
     * @param updateProduct The request containing updated product details.
     * @param productId     The ID of the product to update.
     * @return The updated ProductDetails.
     */
	public ProductDetails updateProduct(UpdateProductRequest updateProduct, int productId) {

		// Retrieve product details from the database based on the provided product ID
		Optional<ProductDetails> optional = productRepository.findById(productId);
		ProductDetails product = (ProductDetails) optional.get();

		// Update product details with the provided information
		product.setBrand(updateProduct.getBrand());
		product.setCategory(updateProduct.getCategory());
		product.setName(updateProduct.getName());
		product.setDetails(updateProduct.getDetails());
		product.setPrice(updateProduct.getPrice());
		
        // Update the maximum price if the updated product's price is greater
        MAXPRICE = Math.max(MAXPRICE, Integer.parseInt(updateProduct.getPrice()));
        
        // Save the updated product details in the database
        productRepository.save(product);

        // Return the updated product details
        return product;	
    }

    /**
     * Retrieves products by category.
     * 
     * @param category The category of the products to retrieve.
     * @return List of ProductDetails in the specified category.
     */
    public List<ProductDetails> getProductsByCategory(String category) {

        // Retrieve products from the database based on the provided category
        List<ProductDetails> list = productRepository.getProductsByCategory(category);

        // Return the list of products in the specified category
        return list;
    }

    /**
     * Retrieves products containing a specific string in their details.
     * 
     * @param str The string to search for in product details.
     * @return List of ProductDetails containing the specified string.
     */
    public List<ProductDetails> getProductsByString(String str) {

        // Retrieve products from the database containing the specified string in details
        List<ProductDetails> list = productRepository.getProductsByString(str);

        // Return the list of products containing the specified string
        return list;
    }

    /**
     * Retrieves filtered products based on the provided criteria.
     * 
     * @param filterObj The filter criteria.
     * @return List of ProductDetails meeting the filter criteria.
     */
    public List<ProductDetails> getFilteredProducts(Filterdto filterObj) {

        // Determine the minimum and maximum prices based on filter criteria
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

        // Retrieve brand and category filter criteria; if not provided, get all brands and categories
        List<String> brand = filterObj.getBrand();
        List<String> category = filterObj.getCategory();

        if (brand == null || brand.size() == 0)
            brand = productRepository.getAllBrands();

        if (category == null || category.size() == 0)
            category = productRepository.getAllCategory();

        // Retrieve filtered products from the database based on the filter criteria
        List<ProductDetails> list = productRepository.getFilteredProducts(minPrice, maxPrice, brand, category);

        // Return the list of filtered products
        return list;
    }   
}

