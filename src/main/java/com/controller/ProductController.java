package com.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.AddProductRequest;
import com.dto.Filterdto;
import com.entity.ImageModel;
import com.entity.ProductDetails;
import com.generic.GenericResponse;
import com.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping(value = {"/addProduct"} , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
	public ResponseEntity<?> addProduct(@RequestPart("product") AddProductRequest addProduct,
				@RequestPart("imageFile") MultipartFile imageFile) {
		ProductDetails product;
		try {
			ImageModel image = uploadImage(imageFile);
			addProduct.setProductImage(image);	
		product = this.productService.addProduct(addProduct);	
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( product, "Product Added Succesfully!", true) , HttpStatus.OK) ;
	}
	
	
	public ImageModel uploadImage(MultipartFile file) throws IOException {
		ImageModel imageModel = new ImageModel(file.getName(), file.getContentType(), file.getBytes());
		
		return imageModel;
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<?> getProduct(@PathVariable("id") int id){
		
		ProductDetails product; 
		try {
		product = this.productService.getProduct(id);
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( product , "Product Fetched Succesfully!", true) , HttpStatus.OK) ;
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> addProduct(@RequestBody ProductDetails updateProduct) {
		ProductDetails product;
		try {
		product = this.productService.updateProduct(updateProduct);
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( product, "Product Updated Succesfully!", true) , HttpStatus.OK) ;
	}
	
	@GetMapping("/{category}")
	public ResponseEntity<?> getProductbyCategory(@PathVariable("category") String category){
		
		List<ProductDetails> products; 
		try {
		products = this.productService.getProductsByCategory(category);;
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( products , "Products Fetched Succesfully!", true) , HttpStatus.OK) ;
		
	}
	
	@GetMapping("/search/{searchString}")
	public ResponseEntity<?> getProductsByString(@PathVariable("searchString") String str){
		
		List<ProductDetails> products; 
		try {
		products = this.productService.getProductsByString(str);;
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( products , "Products Fetched Succesfully!", true) , HttpStatus.OK) ;
		
	}
	
	@PostMapping("/getFilteredProducts")
	public ResponseEntity<?> getFilteredProducts(@RequestBody Filterdto filterObj){
		
		List<ProductDetails> products; 
		try {
		products = this.productService.getFilteredProducts(filterObj);;
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( products , "Products Fetched Succesfully!", true) , HttpStatus.OK) ;
		
	}
	
}
