package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.LoginRequest;
import com.dto.SignupRequest;
import com.dto.UpdateUserRequest;
import com.entity.UserDetails;
import com.repository.AddressRepository;
import com.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepo;


	public UserDetails createUser(SignupRequest signupRequest) {

		UserDetails user = new UserDetails();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(signupRequest.getPassword());
	
		user.setRole("user");

		userRepository.save(user);

		return user;

	}
	
	public UserDetails createSeller(SignupRequest signupRequest) {
		UserDetails user = new UserDetails();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(signupRequest.getPassword());
	
		user.setRole("seller");

		userRepository.save(user);

		return user;
	}

	public UserDetails updateProfile(UpdateUserRequest updateUser) {

		int id = updateUser.getId();
		Optional<UserDetails> optional = userRepository.findById(id);
		UserDetails user = (UserDetails) optional.get();

		user.setName(updateUser.getName());
		user.setPhone(updateUser.getPhone());
		
		if(user.getAddress() != null) {
		int addressId = user.getAddress().getId();
		user.setAddress(updateUser.getAddress());
		
		addressRepo.deleteById(addressId);
		}
		
		else user.setAddress(updateUser.getAddress());
		
		userRepository.save(user);
		return user;

	}

	public Boolean loginUser(LoginRequest loginRequest) {
		
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Optional<UserDetails> userOptional =  userRepository.ifCorrectCredentials(email, password);
		
		if(userOptional.isPresent()) return true;
		else return false;

	}

	public UserDetails getProfile(int userID) {

		Optional<UserDetails> optional = userRepository.findById(userID);
		UserDetails user = (UserDetails) optional.get();

		return user;
	}

	public UserDetails logout(int id) {
		Optional<UserDetails> optional = userRepository.findById(id);
		UserDetails user = (UserDetails) optional.get();

		return user;
	}

}
