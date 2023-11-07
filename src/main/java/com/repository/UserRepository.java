package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Cart;
import com.entity.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Integer>{

	@Query("SELECT u.cart FROM UserDetails u WHERE u.userID = :id")
	Cart findCartByUserId(@Param("id") int userId);
	
	@Query("SELECT u FROM UserDetails u WHERE u.email = :email AND u.password = :password")
	Optional<UserDetails> ifCorrectCredentials(@Param("email") String email, @Param("password") String password);
	
}
