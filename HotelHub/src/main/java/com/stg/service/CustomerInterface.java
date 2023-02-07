package com.stg.service;


import java.time.LocalDate;
import java.util.List;


import com.stg.entity.Availability;
import com.stg.entity.Customer;
import com.stg.entity.CustomerCart;
import com.stg.entity.Hotel;
import com.stg.entity.Payment;
import com.stg.entity.Room;
import com.stg.entity.RoomSize;
import com.stg.entity.RoomType;
import com.stg.exception.BookingException;

public interface CustomerInterface {

	public abstract Customer createNewCustomer(String newName, long phNo, String email,int age, String password)
			throws BookingException;
	
	public abstract List<Room> findCartRooms( int hotelId, long custNo)throws BookingException;
	
	public abstract Customer createCustomer(Customer customer)
			throws BookingException;
	
	public abstract CustomerCart bookRoom(int roomNo, Payment payType, LocalDate checkIn, LocalDate checkOut,
			long phNo) throws BookingException;
	
	public abstract CustomerCart findCart(long phNo) throws BookingException;

	public abstract Customer findCustomerByPhNo(long phNo) throws BookingException;

	public abstract List<Hotel> searchCart(long phNo) throws BookingException;

	public abstract List<Hotel> searchByRoomSize(RoomSize roomSize) throws BookingException;

	public abstract List<Hotel> searchByAvailability(Availability availability) throws BookingException;

	public abstract List<Hotel> searchByCost(Float roomCost) throws BookingException;

	public abstract List<Hotel> searchByRoomType(RoomType roomType) throws BookingException;
	
	public abstract List<Hotel> searchByHotelName(String hotelName) throws BookingException;
	
	public abstract List<Hotel> searchHotelsByCity(String city) throws BookingException;

	public abstract CustomerCart addToCartById(int roomId, long phNo, LocalDate checkIn , LocalDate checkout) throws BookingException;
	
	public abstract CustomerCart addToCartByPhNo(int roomId, long PhNo)throws BookingException;

	public abstract float totalcost(long phNo) throws BookingException;

	public abstract CustomerCart placeOrder(Payment payType, LocalDate checkIn, LocalDate checkOut,
			long phNo) throws BookingException;
	
	public abstract CustomerCart cancelOrder(long phNo,int roomNo) throws BookingException;

	public abstract Customer updateCustomerName(long customerPhone, String newName) throws BookingException;

	public abstract CustomerCart deleteCustomerCart(long phNo) throws BookingException;

}
