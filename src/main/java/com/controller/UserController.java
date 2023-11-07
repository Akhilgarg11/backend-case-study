package com.controller;

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
import com.entity.UserDetails;
import com.generic.GenericResponse;
import com.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String hello() {
		return "Hello World";
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Boolean correctCredentialsOrNot; 
		try {
			correctCredentialsOrNot = this.userService.loginUser(loginRequest);
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( correctCredentialsOrNot , "----" , true) , HttpStatus.OK) ;
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
		UserDetails user; 
		try {
		user = this.userService.createUser(signupRequest);
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( "userID:" + user.getUserID(), "Account Created Succesfully!", true) , HttpStatus.OK) ;
	}
	
	@PostMapping("/seller/signup")
	public ResponseEntity<?> sellerSignup(@RequestBody SignupRequest signupRequest) {
		UserDetails user; 
		try {
		user = this.userService.createUser(signupRequest);
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( "userID:" + user.getUserID(), "Seller Account Created Succesfully!", true) , HttpStatus.OK) ;
	}
	
	@PostMapping("/updateProfile")
	public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUser) {
		UserDetails user; 
		try {
		user = this.userService.updateProfile(updateUser);
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( user, "User Profile Updated Succesfully!", true) , HttpStatus.OK) ;
	}
	
	
	
	@GetMapping("/getprofile/{id}")
	public ResponseEntity<?> getProfile(@PathVariable("id") int id){
		
		UserDetails user; 
		try {
		user = this.userService.getProfile(id);
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( user , "User Fetched Succesfully!", true) , HttpStatus.OK) ;
		
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody int userId) {
		UserDetails user;
		try {
			user = this.userService.logout(userId);
		} catch(Exception e) {
			return new ResponseEntity<>(new GenericResponse<>(null, e.getMessage(), false) , HttpStatus.BAD_REQUEST) ;
		} 
		
		return new ResponseEntity<>(new GenericResponse<>( "user with id:" +user.getUserID(), "Usser logged out Succesfully!", true) , HttpStatus.OK) ;
	}
	
}
