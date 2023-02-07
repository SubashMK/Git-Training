package com.stg.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stg.entity.Availability;
import com.stg.entity.Customer;
import com.stg.entity.CustomerCart;
import com.stg.entity.Hotel;
import com.stg.entity.Payment;
import com.stg.entity.Room;
import com.stg.entity.RoomSize;
import com.stg.entity.RoomType;
import com.stg.exception.BookingException;
import com.stg.service.CustomerImpl;

@RestController
@RequestMapping(value = "customer")
@CrossOrigin(origins = "*")
public class CustomerController {

	@Autowired
	private CustomerImpl customerImpl;

	@GetMapping(value = "findbyphno/{phNo}")
	public ResponseEntity<Customer> findByCustomerPhNo(@PathVariable long phNo) throws BookingException {
		Customer customer = customerImpl.findCustomerByPhNo(phNo);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@PostMapping(value = "createcustomer/{newName}/{phNo}/{email}/{age}/{password}")
	public ResponseEntity<Customer> createNewCustomer(@PathVariable String newName, @PathVariable long phNo,
			@PathVariable String email, @PathVariable int age, @PathVariable String password) throws BookingException {
		Customer customer = customerImpl.createNewCustomer(newName, phNo, email, age, password);

		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@PostMapping(value = "newcustomer")
	public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer customer) throws BookingException {
		Customer cust = customerImpl.createCustomer(customer);
		return new ResponseEntity<Customer>(cust, HttpStatus.OK);
	}

	@GetMapping(value = "searchbyroomsize")
	public ResponseEntity<List<Hotel>> searchByRoomSize(@RequestParam RoomSize roomSize) throws BookingException {
		List<Hotel> hotel = customerImpl.searchByRoomSize(roomSize);
		return new ResponseEntity<List<Hotel>>(hotel, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "searchbyroomavailability")
	public ResponseEntity<List<Hotel>> searchByAvailability(@RequestParam Availability availability)
			throws BookingException {
		List<Hotel> hotels = customerImpl.searchByAvailability(availability);
		return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "searchbyroomcost")
	public ResponseEntity<List<Hotel>> searchByCost(@RequestParam Float roomCost) throws BookingException {
		List<Hotel> hotels = customerImpl.searchByCost(roomCost);
		return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "searchbyroomtype")
	public ResponseEntity<List<Hotel>> searchByRoomType(@RequestParam RoomType roomType) throws BookingException {
		List<Hotel> hotels = customerImpl.searchByRoomType(roomType);
		return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.ACCEPTED);
	}

	@PutMapping(value = "addtocart/{checkIn}/{checkOut}")
	public ResponseEntity<CustomerCart> addToCartById(@RequestParam int roomId, @RequestParam long custPhNo,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkIn,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOut) throws BookingException {
		CustomerCart cart = customerImpl.addToCartById(roomId, custPhNo, checkIn, checkOut);
		return new ResponseEntity<CustomerCart>(cart, HttpStatus.OK);
	}

	@PutMapping(value = "updatecustomername/{customerPhone}/{newName}")
	public ResponseEntity<Customer> updateCustomerName(@PathVariable long customerPhone, @PathVariable String newName)
			throws BookingException {
		Customer customer = customerImpl.updateCustomerName(customerPhone, newName);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@PutMapping(value = "placeorder/{payType}/{checkIn}/{checkOut}/{phNo}")
	public ResponseEntity<CustomerCart> placeOrder(@PathVariable Payment payType,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkIn,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOut, @PathVariable long phNo)
			throws BookingException {
		CustomerCart cart = customerImpl.placeOrder(payType, checkIn, checkOut, phNo);
		return new ResponseEntity<CustomerCart>(cart, HttpStatus.OK);
	}

	@GetMapping(value = "totalcost")
	public ResponseEntity<Float> totalcost(@RequestParam long phNo) throws BookingException {
		float totalCost = customerImpl.totalcost(phNo);
		return new ResponseEntity<Float>(totalCost, HttpStatus.OK);
	}

	@DeleteMapping(value = "deletecart")
	public ResponseEntity<CustomerCart> deleteCustomerCart(@RequestParam long phNo) throws BookingException {
		CustomerCart cart = customerImpl.deleteCustomerCart(phNo);
		return new ResponseEntity<CustomerCart>(cart, HttpStatus.OK);
	}

	@GetMapping(value = "searchcart/{phNo}")
	public ResponseEntity<List<Hotel>> searchCart(@PathVariable long phNo) throws BookingException {
		List<Hotel> hotel = customerImpl.searchCart(phNo);
		return new ResponseEntity<List<Hotel>>(hotel, HttpStatus.OK);
	}

	@GetMapping(value = "searchhotelbyname")
	public ResponseEntity<List<Hotel>> searchHotelByName(@RequestParam String hotelName) throws BookingException {
		List<Hotel> hotels = customerImpl.searchByHotelName(hotelName);
		return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.OK);
	}

	@GetMapping(value = "searchhotelbycity")
	public ResponseEntity<List<Hotel>> searchHotelByCity(@RequestParam String city) throws BookingException {
		List<Hotel> hotels = customerImpl.searchHotelsByCity(city);
		return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.OK);
	}

	@PutMapping(value = "addtocartbyphno/{roomId}/{custPhNo}")
	public ResponseEntity<CustomerCart> addToCartByPhNo(@PathVariable int roomId, @PathVariable long custPhNo)
			throws BookingException {
		CustomerCart cart = customerImpl.addToCartByPhNo(roomId, custPhNo);
		return new ResponseEntity<CustomerCart>(cart, HttpStatus.OK);
	}

	@PutMapping(value = "cancelOrder/{phNo}/{roomNo}")
	public ResponseEntity<CustomerCart> cancelOrder(@PathVariable long phNo, @PathVariable int roomNo)
			throws BookingException {
		CustomerCart cart = customerImpl.cancelOrder(phNo, roomNo);
		return new ResponseEntity<CustomerCart>(cart, HttpStatus.OK);
	}

	@GetMapping(value = "findcartrooms/{custNo}/{hotelId}")
	public ResponseEntity<List<Room>> findCartRooms(@PathVariable int hotelId, @PathVariable long custNo)
			throws BookingException {
		List<Room> rooms = customerImpl.findCartRooms(hotelId, custNo);
		return new ResponseEntity<List<Room>>(rooms, HttpStatus.OK);
	}

	@GetMapping(value = "findcart/{phNo}")
	public ResponseEntity<CustomerCart> findCart(@PathVariable long phNo) throws BookingException {
		CustomerCart cart = customerImpl.findCart(phNo);
		return new ResponseEntity<CustomerCart>(cart, HttpStatus.OK);
	}

	@PutMapping(value = "bookroom/{roomNo}/{payType}/{checkIn}/{checkOut}/{phNo}")
	public ResponseEntity<CustomerCart> bookRoom(@PathVariable Integer roomNo, @PathVariable Payment payType,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkIn,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOut, @PathVariable long phNo)
			throws BookingException {
		CustomerCart cart = customerImpl.bookRoom(roomNo, payType, checkIn, checkOut, phNo);
		return new ResponseEntity<CustomerCart>(cart, HttpStatus.OK);
	}
}