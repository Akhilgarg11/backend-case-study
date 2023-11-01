package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

}
