package com.stg.service;

import java.util.List;

import com.stg.entity.Admin;
import com.stg.entity.Customer;
import com.stg.entity.Hotel;
import com.stg.entity.Rating;
import com.stg.exception.BookingException;

public interface AdminInterface {

	public abstract Admin createAdmin(long phNo, String nameString, String password) throws BookingException;

	public abstract Hotel createHotel(long adminId,String password, String hotelNameString, Rating rating, int pincode, String city, String state)
			throws BookingException;

	public abstract List<Hotel> deleteHotelByHotelId(int hotelId) throws BookingException;

	public abstract List<Hotel> showAllHotels() throws BookingException;

	public abstract Admin updateAdminById(long adminId, String newPassword, String oldPassword) throws BookingException;

	public abstract Customer createNewCustomer(String newName, long phNo, String email, int age)
			throws BookingException;

	public abstract Customer findCustomerByPhNo(long phNo) throws BookingException;
	
	public abstract List<Customer> readAllCustomer() throws BookingException;

	public abstract Admin findByAdminNo(long phNo) throws BookingException;

}
