package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.UserAddress;

public interface AddressRepository extends JpaRepository<UserAddress, Integer> {

}
