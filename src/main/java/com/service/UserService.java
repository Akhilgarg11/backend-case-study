package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.LoginRequest;
import com.dto.SignupRequest;
import com.entity.UserDetails;
import com.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressService addressService;

	public UserDetails createUser(SignupRequest signupRequest) {

		UserDetails user = new UserDetails();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(signupRequest.getPassword());

		userRepository.save(user);

		return user;

	}

	public UserDetails updateProfile(UserDetails updateUser) {

		int id = updateUser.getUserID();
		Optional<UserDetails> optional = userRepository.findById(id);
		UserDetails user = (UserDetails) optional.get();

		user.setEmail(updateUser.getEmail());
		user.setName(updateUser.getName());
		user.setPassword(updateUser.getPassword());
		user.setPhone(updateUser.getPhone());
		
		if(user.getAddress() != null) {
		int addressId = user.getAddress().getId();
		user.setAddress(updateUser.getAddress());
		addressService.deleteAddress(addressId);
		}
		
		else user.setAddress(updateUser.getAddress());
		
		userRepository.save(user);
		return user;

	}

	public void loginUser(LoginRequest loginRequest) {

		UserDetails user = new UserDetails();
		user.setEmail(loginRequest.getEmail());
		user.setPassword(loginRequest.getPassword());

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
