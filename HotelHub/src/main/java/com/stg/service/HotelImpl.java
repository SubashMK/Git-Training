package com.stg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stg.entity.Availability;
import com.stg.entity.CustomerCart;
import com.stg.entity.Hotel;
import com.stg.entity.HotelAddress;
import com.stg.entity.Rating;
import com.stg.entity.Room;
import com.stg.entity.RoomSize;
import com.stg.entity.RoomType;
import com.stg.exception.BookingException;
import com.stg.repository.HotelRepository;
import com.stg.repository.RoomRepository;

@Service
public class HotelImpl implements HotelInterface {

	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private RoomRepository roomRepository;

	@Override
	public Hotel updatHotelById(int hotelId, String newHotelName) throws BookingException {
		if (hotelId != 0 && newHotelName != null) {
			Hotel hotel = hotelRepository.findById(hotelId).get();
			hotel.setHotelName(newHotelName);
			return hotelRepository.save(hotel);
		} else {
			throw new BookingException("Give appropriate input");
		}
	}

	@Override
	public Hotel addRoom(int hotelId, int doorNo, Availability availability, RoomType roomType, RoomSize roomSize,
			float cost) throws BookingException {
		if (hotelId != 0 && cost != 0f && roomType != null && roomSize != null && doorNo != 0) {
			Room room = new Room();
			Hotel hotel = new Hotel();
			hotel = hotelRepository.findById(hotelId).get();
			room.setDoorNo(doorNo);
			room.setCost(cost);
			room.setAvailability(availability);
			room.setRoomSize(roomSize);
			room.setRoomType(roomType);
			room.setHotelRef(hotel);
			hotel.getRooms().add(room);
			roomRepository.save(room);
			hotelRepository.save(hotel);
			return hotel;
		} else {
			throw new BookingException("Input Not valid");
		}
	}

	@Override
	public List<Room> readAllRooms(int hotelId) throws BookingException {
		if (hotelId != 0) {
			List<Room> hotelRooms = new ArrayList<Room>();
			if (hotelRepository.findById(hotelId).get() != null) {
				hotelRooms = hotelRepository.findById(hotelId).get().getRooms();
			} else {
				throw new BookingException("HotelId is in correct");
			}
			return hotelRooms;
		} else {
			throw new BookingException("Input should not be empty");
		}
	}

	@Override
	public Hotel deleteRoomByNo(int roomNo, int hotelId) throws BookingException {
		if (roomNo != 0) {
			if (hotelRepository.findById(hotelId).get() != null) {
				Hotel hotel = hotelRepository.findById(hotelId).get();
						roomRepository.deleteById(roomNo);
				//roomRepository.saveAll(hotelRooms);
				hotelRepository.save(hotel);
				return hotel;
			} else {
				throw new BookingException("HotelId is in correct");
			}
		} else {
			throw new BookingException("Give appropriate input");
		}
	}

	@Override
	public List<Hotel> findByName(String hotelName) throws BookingException {
		if (hotelName != null) {
			List<Hotel> hotels = hotelRepository.findAllByHotelName(hotelName);
			return hotels;
		} else {
			throw new BookingException("Input should not be empty");
		}

	}

	@Override
	public Hotel createHotel(String hotelName, Rating rating, long pincode, String city, String state,String password)
			throws BookingException {
		if (hotelName != null && rating != null && password!=null && city != null && state != null && pincode != 0) {
			Hotel hotel = new Hotel();
			hotel.setHotelName(hotelName);
			hotel.setRating(rating);
			hotel.setPassword(password);
			HotelAddress address = new HotelAddress();
			address.setCity(city);
			address.setState(state);
			address.setPincode(pincode);
			hotel.setAddress(address);
			return hotelRepository.save(hotel);
		} else {
			throw new BookingException("Give the full details");
		}
	}

	@Override
	public Hotel findByHotelId(int hotelId) throws BookingException {
		Hotel  temp =null;
		Hotel user = hotelRepository.findById(hotelId).get();
		if(user.getHotelNo() == hotelId) {
			temp = user;
		}else {
			throw new BookingException("this username is not present");
		}
		return temp;
	}

	@Override
	public List<CustomerCart> findCustById(int hotelId) throws BookingException {
		if (hotelId!=0) {
			Hotel hotel = hotelRepository.findById(hotelId).get();
			List<Room> rooms = hotel.getRooms();
			List<CustomerCart> carts = new ArrayList<CustomerCart>();
			for (Room room : rooms) {
				if (room.getAvailability().equals(Availability.BOOKED)) {
					carts.add(room.getCartRef()) ;
				}
			}
			return carts;
		}else {
			throw new BookingException("Invalid Input");
		}
		
	}

}
