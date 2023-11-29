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

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepo;
	
	public boolean getAccountByEmail(SignupRequest signupRequest) {
		String email = signupRequest.getEmail();
		Optional<UserDetails> optional = userRepository.getUserByEmail(email);
		
		if(!optional.isEmpty()) {
			return true;
		}
		return false;
	}

	public UserDetails createUser(SignupRequest signupRequest) throws Exception {
		String email = signupRequest.getEmail();
		Optional<UserDetails> optional = userRepository.getUserByEmail(email);
		
		if(!optional.isEmpty()) {
			throw new Exception("Account with this email already exists");
		}
		
		else {

		UserDetails user = new UserDetails();
		user.setEmail(signupRequest.getEmail());
		user.setName(signupRequest.getName());
		user.setPassword(signupRequest.getPassword());

		user.setRole("user");

		userRepository.save(user);

		return user;
		}	
	}
	
	public UserDetails createUserNew(SignupRequest signupRequest) {


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

	@Transactional
	public UserDetails updateProfile(UpdateUserRequest updateUser) {

		int id = updateUser.getId();
		Optional<UserDetails> optional = userRepository.findById(id);
		UserDetails user = (UserDetails) optional.get();

		user.setName(updateUser.getName());
		user.setPhone(updateUser.getPhone());

		if (user.getAddress() != null) {
			int addressId = user.getAddress().getId();
			user.setAddress(updateUser.getAddress());

			addressRepo.deleteById(addressId);
		}

		else
			user.setAddress(updateUser.getAddress());

		userRepository.save(user);
		return user;

	}

	public int loginUser(LoginRequest loginRequest) {

		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		Optional<UserDetails> userOptional = userRepository.ifCorrectUserCredentials(email, password);

		if (userOptional.isEmpty())
			return -1;

		UserDetails user = userOptional.get();
		return user.getUserID();

	}

	public int loginSeller(LoginRequest loginRequest) {
		String email = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		Optional<UserDetails> userOptional = userRepository.ifCorrectSellerCredentials(email, password);

		if (userOptional.isEmpty())
			return -1;

		UserDetails user = userOptional.get();
		return user.getUserID();

	}

	public UserResponse getProfile(int userID) {

		Optional<UserDetails> optional = userRepository.findById(userID);
		UserDetails user = (UserDetails) optional.get();
		
		UserResponse userProfile = new UserResponse();
		
		userProfile.setAddress(user.getAddress());
		userProfile.setCart(user.getCart());
		userProfile.setEmail(user.getEmail());
		userProfile.setName(user.getName());
		userProfile.setPhone(user.getPhone());
//		userProfile.setUserOrders(user.getUserOrders());
		
		return userProfile;
	}

	public UserDetails logout(int id) {
		Optional<UserDetails> optional = userRepository.findById(id);
		UserDetails user = (UserDetails) optional.get();

		return user;
	}

}
