package com.stg.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stg.entity.CustomerCart;

@Repository
public interface CustomerCartRepository extends JpaRepository<CustomerCart, Integer> {

	public abstract CustomerCart findByCustomerPhNo(long phNo);

	
}
