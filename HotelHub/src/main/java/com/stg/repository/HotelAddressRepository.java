package com.stg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stg.entity.HotelAddress;

@Repository
public interface HotelAddressRepository extends JpaRepository<HotelAddress, Integer>{

}
