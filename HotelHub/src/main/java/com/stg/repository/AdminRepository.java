package com.stg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stg.entity.Admin;
import com.stg.entity.Customer;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
	public abstract Admin findByAdminPhNo(long adminPhNo);
}
