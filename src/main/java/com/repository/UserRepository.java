package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.Cart;
import com.entity.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, Integer>{

	@Query("SELECT u.cart FROM UserDetails u WHERE u.userID = :id")
	Cart findCartByUserId(@Param("id") int userId);
	
}
