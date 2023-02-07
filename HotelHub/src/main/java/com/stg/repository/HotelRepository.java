package com.stg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stg.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer>{

	@Query(value = "select * from hotel where hotel_name =:name",nativeQuery = true)
	public abstract List<Hotel> findAllByHotelName(@Param("name") String hotelName);
	
	
	
}
