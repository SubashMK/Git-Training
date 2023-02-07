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

import com.stg.entity.Admin;
import com.stg.entity.Customer;
import com.stg.entity.Hotel;
import com.stg.entity.Rating;
import com.stg.exception.BookingException;
import com.stg.service.AdminImpl;

@RestController
@RequestMapping(value = "admin")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private AdminImpl adminImpl;

	@GetMapping(value = "findbyadminno/{phNo}")
	public ResponseEntity<Admin> findByAdminByNo(@PathVariable long phNo) throws BookingException {
Admin admin = adminImpl.findByAdminNo(phNo);
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}
	
	
	
	@PostMapping(value = "createhotel")
	public ResponseEntity<Hotel> createHotel(@RequestParam long adminId, @RequestParam String password,
			@RequestParam String hotelName, @RequestParam Rating rating, @RequestParam int pincode,
			@RequestParam String city, @RequestParam String state) throws BookingException {
		Hotel hotel = adminImpl.createHotel(adminId, password, hotelName, rating, pincode, city, state);
		return new ResponseEntity<Hotel>(hotel, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "deletehotel/{hotelId}")
	public ResponseEntity<List<Hotel>> deleteHotelByHotelId(@PathVariable int hotelId) throws BookingException {
		List<Hotel> hotels = adminImpl.deleteHotelByHotelId(hotelId);
		return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.OK);
	}

	@GetMapping(value = "showallhotels")
	public ResponseEntity<List<Hotel>> showAllHotels() throws BookingException {
		List<Hotel> hotels = adminImpl.showAllHotels();
		return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.OK);
	}

	@PostMapping(value = "createadmin/{phNo}/{name}/{password}")
	public ResponseEntity<Admin> createAdmin(@PathVariable long phNo, @PathVariable String name,
			@PathVariable String password) throws BookingException {
		Admin admin = adminImpl.createAdmin(phNo, name, password);
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}

	@PutMapping(value = "updatebyid")
	public ResponseEntity<Admin> updateAdminById(@RequestParam long adminId, @RequestParam String newPassword,
			@RequestParam String oldPassword) throws BookingException {
		Admin admin = adminImpl.updateAdminById(adminId, newPassword, oldPassword);
		return new ResponseEntity<Admin>(admin, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "createcustomer")
	public ResponseEntity<Customer> createNewCustomer(@RequestParam String newName, @RequestParam long phNo,
			@RequestParam String email, @RequestParam int age) throws BookingException {
		Customer customer = adminImpl.createNewCustomer(newName, phNo, email, age);
		return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
	}

	@GetMapping(value = "findbyphno")
	public ResponseEntity<Customer> findByCustomerPhNo(@RequestParam long phNo) throws BookingException {
		Customer customer = adminImpl.findCustomerByPhNo(phNo);
		return new ResponseEntity<Customer>(customer, HttpStatus.FOUND);
	}

	@GetMapping(value = "allcust")
	public ResponseEntity<List<Customer>> readAllCustomer() throws BookingException {
		 List<Customer> customers = adminImpl.readAllCustomer();
			return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}
}
