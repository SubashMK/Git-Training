package com.stg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stg.entity.Availability;
import com.stg.entity.CustomerCart;
import com.stg.entity.Hotel;
import com.stg.entity.Rating;
import com.stg.entity.Room;
import com.stg.entity.RoomSize;
import com.stg.entity.RoomType;
import com.stg.exception.BookingException;
import com.stg.service.HotelImpl;

@RestController
@RequestMapping(value = "hoteladmin")
@CrossOrigin(value = "*")
public class HotelController {

	@Autowired
	private HotelImpl hotelImpl;

	@PutMapping(value = "updatebyid")
	public ResponseEntity<Hotel> updatHotelById(@RequestParam int hotelId, @RequestParam String newHotelName)
			throws BookingException {
		Hotel hotel = hotelImpl.updatHotelById(hotelId, newHotelName);
		return new ResponseEntity<Hotel>(hotel, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "addroom/{hotelId}/{doorNo}/{roomType}/{availability}/{roomSize}/{cost}")
	public ResponseEntity<Hotel> addRoom(@PathVariable int hotelId, @PathVariable int doorNo,
			@PathVariable RoomType roomType, @PathVariable Availability availability, @PathVariable RoomSize roomSize,
			@PathVariable float cost) throws BookingException {
		Hotel hotel = hotelImpl.addRoom(hotelId, doorNo, availability, roomType, roomSize, cost);
		return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
	}

	@GetMapping(value = "readallrooms/{hotelId}")
	public ResponseEntity<List<Room>> readAllRooms(@PathVariable int hotelId) throws BookingException {
		List<Room> rooms = hotelImpl.readAllRooms(hotelId);
		return new ResponseEntity<List<Room>>(rooms, HttpStatus.OK);
	}

	@DeleteMapping(value = "deleteroombyno")
	public ResponseEntity<Hotel> deleteRoomByNo(@RequestParam int roomNo, @RequestParam int hotelId)
			throws BookingException {
		Hotel hotel = hotelImpl.deleteRoomByNo(roomNo, hotelId);
		return new ResponseEntity<Hotel>(hotel, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "readbyname")
	public ResponseEntity<List<Hotel>> findByName(@RequestParam String hotelName) throws BookingException {
		List<Hotel> hotels = hotelImpl.findByName(hotelName);
		return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.FOUND);
	}

	@PostMapping(value = "createhotel/{hotelName}/{rating}/{pincode}/{city}/{state}/{password}")
	public ResponseEntity<Hotel> createHotel(@PathVariable String hotelName, @PathVariable Rating rating,
			@PathVariable("pincode") Long pincode, @PathVariable String city, @PathVariable String state,@PathVariable String password) throws BookingException {
		Hotel hotel = hotelImpl.createHotel(hotelName, rating, pincode, city, state,password);
		return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
	}

	@GetMapping(value = "hotelbyid/{hotelId}")
	public ResponseEntity<Hotel> findByHotelId(@PathVariable int hotelId) throws BookingException {
		Hotel hotel = hotelImpl.findByHotelId(hotelId);
		return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
	}
	
	@GetMapping(value = "cartbyid/{hotelId}")
	public ResponseEntity<List<CustomerCart>> findCustById(@PathVariable int hotelId) throws BookingException {
		List<CustomerCart> carts = hotelImpl.findCustById(hotelId);
		return new ResponseEntity<List<CustomerCart>>(carts, HttpStatus.OK);
	}
}
