package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.UserOrder;

public interface OrderRepository extends JpaRepository<UserOrder, Integer> {

}
