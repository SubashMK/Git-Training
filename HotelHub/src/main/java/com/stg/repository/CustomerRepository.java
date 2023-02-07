package com.stg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stg.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	public abstract Customer findByCustomerPhNo(long phNo);
	
	@Query(value = "select *from customer where customer_id =:custId",nativeQuery = true)
	public abstract Customer customId(@Param("custId") int custId);
}
