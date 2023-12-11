// Import necessary classes and interfaces
package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.LoginRequest;
import com.dto.SignupRequest;
import com.dto.UpdateUserRequest;
import com.dto.UserResponse;
import com.entity.UserDetails;
import com.repository.AddressRepository;
import com.repository.UserRepository;

// Declare the class as a Spring service
@Service
public class UserService {

	// Autowire UserRepository for database operations related to users
	@Autowired
	private UserRepository userRepository;

	// Autowire AddressRepository for database operations related to addresses
	@Autowired
	private AddressRepository addressRepo;

    /**
     * Checks if an account with the given email already exists.
     * 
     * @param signupRequest The signup request containing the email to check.
     * @return true if the account exists, false otherwise.
     */
	public boolean getAccountByEmail(SignupRequest signupRequest) {
		// Extract email from the signup request
		String email = signupRequest.getEmail();
		
		// Check if a user with the given email already exists in the database
		Optional<UserDetails> optional = userRepository.getUserByEmail(email);
		
		// Return true if a user with the given email exists, otherwise false
		if(!optional.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
     * Creates a new user account.
     * 
     * @param signupRequest The signup request containing user details.
     * @return The created user details.
     * @throws Exception If an account with the same email already exists.
     */
	public UserDetails createUser(SignupRequest signupRequest) throws Exception {
		// Extract email from the signup request
		String email = signupRequest.getEmail();
		
		// Check if a user with the given email already exists in the database
		Optional<UserDetails> optional = userRepository.getUserByEmail(email);
		
		// Throw an exception if a user with the given email already exists
		if(!optional.isEmpty()) {
			throw new Exception("Account with this email already exists");
		}
		
		// If no existing user, create a new user with the provided details
		else {
			UserDetails user = new UserDetails();
			user.setEmail(signupRequest.getEmail());
			user.setName(signupRequest.getName());
			user.setPassword(signupRequest.getPassword());
			user.setRole("user");

			// Save the new user in the database
			userRepository.save(user);

			// Return the created user details
			return user;
		}	
	}
	
	/**
     * Creates a new user account without checking for existing accounts.
     * 
     * @param signupRequest The signup request containing user details.
     * @return The created user details.
     */
	public UserDetails createUserNew(SignupRequest signupRequest) {
		// Create a new user with the provided details
		UserDetails user = new UserDetails();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(signupRequest.getPassword());
		user.setRole("user");

		// Save the new user in the database
		userRepository.save(user);

		// Return the created user details
		return user;
	}

	/**
     * Creates a new seller account.
     * 
     * @param signupRequest The signup request containing seller details.
     * @return The created seller details.
     */
	public UserDetails createSeller(SignupRequest signupRequest) {
		// Create a new seller with the provided details
		UserDetails user = new UserDetails();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(signupRequest.getPassword());
		user.setRole("seller");

		// Save the new seller in the database
		userRepository.save(user);

		// Return the created seller details
		return user;
	}

	 /**
     * Updates the user profile.
     * 
     * @param updateUser The request containing updated user details.
     * @return The updated user details.
     */
	@Transactional
	public UserDetails updateProfile(UpdateUserRequest updateUser) {
		// Extract user ID from the update request
		int id = updateUser.getId();
		
		// Retrieve the existing user details from the database
		Optional<UserDetails> optional = userRepository.findById(id);
		UserDetails user = (UserDetails) optional.get();

		// Update user details with the provided information
		user.setName(updateUser.getName());
		user.setPhone(updateUser.getPhone());

		// If the user already has an address, update it; otherwise, set a new address
		if (user.getAddress() != null) {
			int addressId = user.getAddress().getId();
			user.setAddress(updateUser.getAddress());

			// Delete the old address from the database
			addressRepo.deleteById(addressId);
		} else {
			// Set a new address for the user
			user.setAddress(updateUser.getAddress());
		}

		// Save the updated user details in the database
		userRepository.save(user);

		// Return the updated user details
		return user;

	}
	
	
	/**
     * Authenticates a user login and returns the user ID.
     * 
     * @param loginRequest The login request containing user credentials.
     * @return The user ID if authentication is successful, -1 otherwise.
     */
	public int loginUser(LoginRequest loginRequest) {
		// Extract email and password from the login request
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		// Check if the provided credentials match a user in the database
		Optional<UserDetails> userOptional = userRepository.ifCorrectUserCredentials(email, password);

		// If credentials are valid, return the user ID; otherwise, return -1
		if (userOptional.isEmpty())
			return -1;

		UserDetails user = userOptional.get();
		return user.getUserID();

	}

	/**
     * Authenticates a seller login and returns the user ID.
     * 
     * @param loginRequest The login request containing seller credentials.
     * @return The user ID if authentication is successful, -1 otherwise.
     */
	public int loginSeller(LoginRequest loginRequest) {
		// Extract email and password from the login request
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		// Check if the provided credentials match a seller in the database
		Optional<UserDetails> userOptional = userRepository.ifCorrectSellerCredentials(email, password);

		// If credentials are valid, return the seller ID; otherwise, return -1
		if (userOptional.isEmpty())
			return -1;

		UserDetails user = userOptional.get();
		return user.getUserID();

	}

	/**
     * Retrieves the user profile information.
     * 
     * @param userID The ID of the user.
     * @return The user profile information.
     */
	public UserResponse getProfile(int userID) {
		// Retrieve user details from the database based on the provided user ID
		Optional<UserDetails> optional = userRepository.findById(userID);
		UserDetails user = (UserDetails) optional.get();
		
		// Create a UserResponse object with the user's profile information
		UserResponse userProfile = new UserResponse();
		userProfile.setAddress(user.getAddress());
		userProfile	.setCart(user.getCart());
		userProfile	.setEmail(user.getEmail());
		userProfile	.setName(user.getName());
		userProfile	.setPhone(user.getPhone());

		// Return the user profile information
		return userProfile;
	}

	/**
     * Logs out the user/seller and returns user details.
     * 
     * @param id The ID of the user to log out.
     * @return The user details after logout.
     */
	public UserDetails logout(int id) {
		// Retrieve user details from the database based on the provided user ID
		Optional<UserDetails> optional = userRepository.findById(id);
		UserDetails user = (UserDetails) optional.get();

		// Return the user details after logout
		return user;
	}
}
