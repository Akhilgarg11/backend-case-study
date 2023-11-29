package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.LoginRequest;
import com.dto.SignupRequest;
import com.dto.UpdateUserRequest;
import com.dto.UserResponse;
import com.entity.ProductDetails;
import com.entity.UserDetails;
import com.generic.GenericResponse;
import com.service.ProductService;
import com.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String hello() {
		return "Hello World";
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		int userId;
		try {
			userId = this.userService.loginUser(loginRequest);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new GenericResponse<>(userId, "----", true) , HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
		UserDetails user;
		try {
			user = this.userService.createUser(signupRequest);
		} catch (Exception e) {
			return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(
				new GenericResponse<>(user.getUserID(), "Account Created Succesfully!", true),
				HttpStatus.OK);
	}
	
//	@PostMapping("/signupNew")
//	public ResponseEntity<?> signupNew(@RequestBody SignupRequest signupRequest) {
//		UserDetails user;
//		try {
//			user = this.userService.createUser(signupRequest);
//		} catch (Exception e) {
//			return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//
//		return new ResponseEntity<>(
//				new GenericResponse<>("userID:" + user.getUserID(), "Account Created Succesfully!", true),
//				HttpStatus.OK);
//	}

	@PostMapping("/seller/login")
	public ResponseEntity<?> sellerLogin(@RequestBody LoginRequest loginRequest) {
		int userId;
		try {
			userId = this.userService.loginSeller(loginRequest);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new GenericResponse<>(userId, "----", true), HttpStatus.OK);
	}

	@PostMapping("/seller/signup")
	public ResponseEntity<?> sellerSignup(@RequestBody SignupRequest signupRequest) {
		UserDetails user;
		try {
			user = this.userService.createSeller(signupRequest);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(
				new GenericResponse<>( user.getUserID(), "Seller Account Created Succesfully!", true),
				HttpStatus.OK);
	}

	@PostMapping("/updateProfile")
	public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUser) {
		UserDetails user;
		try {
			user = this.userService.updateProfile(updateUser);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new GenericResponse<>(user, "User Profile Updated Succesfully!", true),
				HttpStatus.OK);
	}

	@GetMapping("/getprofile/{id}")
	public ResponseEntity<?> getProfile(@PathVariable("id") int id) {

		UserResponse user;
		try {
			user = this.userService.getProfile(id);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>( user, HttpStatus.OK);

	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody int userId) {
		UserDetails user;
		try {
			user = this.userService.logout(userId);
		} catch (Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(
				new GenericResponse<>("user with id:" + user.getUserID(), "Usser logged out Succesfully!", true),
				HttpStatus.OK);
	}

	@GetMapping("/seller/getProducts/{sellerId}")
	public ResponseEntity<?> getProductBySellerId(@PathVariable("sellerId") int sellerId) {

		List<ProductDetails> products;
		try {
			products = this.productService.getProductsBySellerId(sellerId);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(products, HttpStatus.OK);

	}
	
	@PostMapping("/getAccountByEmail")
	public ResponseEntity<?> getAccountByEmail(@RequestBody SignupRequest signupRequest){
		boolean res;
		try {
			res = this.userService.getAccountByEmail(signupRequest);
		} catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
