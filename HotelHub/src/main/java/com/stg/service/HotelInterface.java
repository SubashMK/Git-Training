package com.stg.service;

import java.util.List;

import com.stg.entity.Availability;
import com.stg.entity.CustomerCart;
import com.stg.entity.Hotel;
import com.stg.entity.Rating;
import com.stg.entity.Room;
import com.stg.entity.RoomSize;
import com.stg.entity.RoomType;
import com.stg.exception.BookingException;

public interface HotelInterface {

	//admin
//	public abstract Hotel createHotel (Hotel hotel) throws BookingException;
	public abstract Hotel createHotel(String hotelName, Rating rating, long pincode, String city, String state,String password)
			throws BookingException;

	public abstract Hotel updatHotelById(int hotelId ,String newHotelName) throws BookingException;
	
	public abstract Hotel addRoom(int hotelId,int roomNo, Availability availability, RoomType roomType,RoomSize roomSize,float cost) throws BookingException;
	
	public abstract List<Room> readAllRooms(int hotelId) throws BookingException;
	
	public abstract Hotel deleteRoomByNo(int roomNo, int hotelId) throws BookingException;
	
	public abstract List<Hotel> findByName(String hotelName) throws BookingException;
	
	public abstract Hotel findByHotelId(int hotelId) throws BookingException;
	
	public abstract List<CustomerCart> findCustById(int hotelId) throws BookingException;

}
