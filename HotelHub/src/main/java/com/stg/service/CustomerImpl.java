package com.stg.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stg.entity.Availability;
import com.stg.entity.CartStatus;
import com.stg.entity.Customer;
import com.stg.entity.CustomerCart;
import com.stg.entity.Hotel;
import com.stg.entity.Payment;
import com.stg.entity.Room;
import com.stg.entity.RoomSize;
import com.stg.entity.RoomType;
import com.stg.exception.BookingException;
import com.stg.repository.CustomerCartRepository;
import com.stg.repository.CustomerRepository;
import com.stg.repository.HotelRepository;
import com.stg.repository.RoomRepository;

@Service
public class CustomerImpl implements CustomerInterface {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private CustomerCartRepository cartRepository;
	@Autowired
	private HotelRepository hotelRepository;

	@Override
	public Customer createNewCustomer(String newName, long phNo, String email, int age, String password)
			throws BookingException {
		if (newName != null && phNo != 0 && email != null && customerRepository.findByCustomerPhNo(phNo) == null
				&& String.valueOf(password).length() > 5) {
			Customer customer = new Customer();
			customer.setCustomerName(newName);
			customer.setCustomerPhNo(phNo);
			customer.setEmail(email);
			customer.setAge(age);
			customer.setPassword(password);
			CustomerCart cart = new CustomerCart();
			cart.setCustomerPhNo(phNo);
			cart.setStatus(CartStatus.NOTBOOKED);
			cartRepository.save(cart);
			customer.setCart(cart);
			return customerRepository.save(customer);
		} else {
			throw new BookingException("invalid input or User already exist");
		}
	}

	@Override
	public Customer findCustomerByPhNo(long phNo) throws BookingException {

		Customer temp = null;
		Customer user = customerRepository.findByCustomerPhNo(phNo);
		if (user.getCustomerPhNo() == phNo) {
			temp = user;
		} else {
			throw new BookingException("this username is not present");
		}
		return temp;

//		if (phNo != 0L) {
//			return customerRepository.findByCustomerPhNo(phNo);
//		} else {
//			throw new BookingException("Phone No must be entered");
//		}
	}

	@Override
	public List<Hotel> searchByRoomSize(RoomSize roomSize) throws BookingException {
		List<Room> resultList = new ArrayList<Room>();
		List<Hotel> tempList = new ArrayList<Hotel>();
		if (roomSize != null) {
			for (Hotel hotel : hotelRepository.findAll()) {
				for (Room room : hotel.getRooms()) {
					if (roomSize.equals(room.getRoomSize())) {
						tempList.add(hotel);
						resultList.add(room);
					}
				}
			}
			for (Hotel hotel : tempList) {
				hotel.setRooms(resultList);
			}
			return tempList;
		} else {
			throw new BookingException("Size Not Available");
		}
	}

	@Override
	public List<Hotel> searchByAvailability(Availability availability) throws BookingException {
		if (availability != null) {
			List<Room> resultList = new ArrayList<Room>();
			List<Hotel> tempList = new ArrayList<Hotel>();
			for (Hotel hotel : hotelRepository.findAll()) {
				for (Room room : hotel.getRooms()) {
					if (availability.equals(room.getAvailability())) {
						tempList.add(hotel);
						resultList.add(room);
					}
				}
			}
			for (Hotel hotel : tempList) {
				hotel.setRooms(resultList);
			}
			return tempList;
		} else {
			throw new BookingException("Size Not Available");
		}
	}

	@Override
	public List<Hotel> searchByCost(Float roomCost) throws BookingException {
		if (roomCost != null) {
			List<Room> resultList = new ArrayList<Room>();
			List<Hotel> tempList = new ArrayList<Hotel>();
			for (Hotel hotel : hotelRepository.findAll()) {
				for (Room room : hotel.getRooms()) {
					if (roomCost >= room.getCost()) {
						tempList.add(hotel);
						resultList.add(room);
					}
				}
			}
			for (Hotel hotel : tempList) {
				hotel.setRooms(resultList);
			}
			return tempList;
		} else {
			throw new BookingException("Size Not Available");
		}
	}

	@Override
	public List<Hotel> searchByRoomType(RoomType roomType) throws BookingException {
		if (roomType != null) {
			List<Room> resultList = new ArrayList<Room>();
			List<Hotel> tempList = new ArrayList<Hotel>();
			for (Hotel hotel : hotelRepository.findAll()) {
				for (Room room : hotel.getRooms()) {
					if (roomType.equals(room.getRoomType())) {
						tempList.add(hotel);
						resultList.add(room);
					}
				}
			}
			for (Hotel hotel : tempList) {
				hotel.setRooms(resultList);
			}
			return tempList;
		} else {
			throw new BookingException("Size Not Available");
		}
	}

	@Override
	public CustomerCart addToCartById(int roomId, long phNo, LocalDate checkIn, LocalDate checkOut)
			throws BookingException {
		if (roomId != 0 && phNo != 0) {
			Room room = new Room();
			room = roomRepository.findById(roomId).get();
			Customer customer = new Customer();
			customer = customerRepository.findByCustomerPhNo(phNo);
			CustomerCart cart = new CustomerCart();
			cart = customer.getCart();
			cart.getRooms().add(room);
			room.setCartRef(cart);
			cart.setCheckIn(checkIn);
			cart.setCheckOut(checkOut);
			roomRepository.save(room);
			cartRepository.save(cart);
			customerRepository.save(customer);
			cart.setTotalCost(totalcost(phNo));
			cartRepository.save(cart);
			return cart;
		} else {
			throw new BookingException("**Invalid Given Data**");
		}
	}

	@Override
	public Customer updateCustomerName(long customerPhone, String newName) throws BookingException {
		if (customerPhone != 0 && newName != null) {
			Customer customer = customerRepository.findByCustomerPhNo(customerPhone);
			customer.setCustomerName(newName);
			return customerRepository.save(customer);
		} else {
			throw new BookingException("**Incorrect given data**");
		}
	}

	@Override
	public float totalcost(long phNo) throws BookingException {
		float result = 0.0f;
		long days = 0;
		List<Room> rooms = new ArrayList<Room>();
		if (phNo != 0) {
			CustomerCart cart = new CustomerCart();
			cart = customerRepository.findByCustomerPhNo(phNo).getCart();
			rooms = cart.getRooms();
			LocalDate checkIn = cart.getCheckIn();
			LocalDate checkOut = cart.getCheckOut();
			days = checkIn.until(checkOut, ChronoUnit.DAYS);
		}
		if (rooms != null) {
			for (Room room : rooms) {
				result += room.getCost() * days;
			}
			return result;
		} else {
			throw new BookingException("Invalid given data");
		}
	}

	public CustomerCart placeOrder(Payment payType, LocalDate checkIn, LocalDate checkOut, long phNo)
			throws BookingException {
		List<Room> rooms = new ArrayList<Room>();
		if (phNo != 0 && checkIn.isAfter(LocalDate.now()) && checkOut.isAfter(LocalDate.now())) {
			Customer customer = new Customer();
			customer = customerRepository.findByCustomerPhNo(phNo);
			CustomerCart cart = new CustomerCart();
			cart = customer.getCart();
			rooms = cart.getRooms();
			for (Room room : rooms) {
				for (LocalDate date = checkIn; date.isBefore(checkOut); date = date.plusDays(1)) {
					if (room.getAvailability() == Availability.NOT_BOOKED && checkIn != LocalDate.now()) {
						room.setAvailability(Availability.BOOKED);
						cart.setStatus(CartStatus.BOOKED);
						cart.setCheckIn(checkIn);
						cart.setCheckOut(checkOut);
						cart.setPaymentType(payType);
					}
				}
			}
			roomRepository.saveAll(rooms);
			cartRepository.save(cart);
			customerRepository.save(customer);
			return cart;
		} else {
			throw new BookingException("Booking Failed");
		}
	}

	@Override
	public CustomerCart deleteCustomerCart(long phNo) throws BookingException {
		if (phNo != 0) {
			Customer customer = new Customer();
			customer = customerRepository.findByCustomerPhNo(phNo);
			if (customer != null) {
				CustomerCart cart = new CustomerCart();
				cart = customer.getCart();
				cart.setCheckIn(null);
				cart.setCheckOut(null);
				List<Room> rooms = new ArrayList<Room>();
				rooms = cart.getRooms();
				if (cart != null && rooms != null) {
					for (Room room : rooms) {
						room.setCartRef(null);
					}
					rooms.clear();
					roomRepository.saveAll(rooms);
					cartRepository.save(cart);
					customerRepository.save(customer);
					return cart;
				} else {
					throw new BookingException("There is no rooms in the cart for now");
				}
			} else {
				throw new BookingException("There is No such customer with this id");
			}
		} else {
			throw new BookingException("Enter valid customer Id");
		}
	}

	@Override
	public List<Hotel> searchCart(long phNo) throws BookingException {
		if (phNo != 0L) {
			List<Room> result = new ArrayList<Room>();
			List<Room> temp1 = new ArrayList<Room>();
			List<Hotel> temp2 = new ArrayList<Hotel>();
			List<Room> rooms = customerRepository.findByCustomerPhNo(phNo).getCart().getRooms();
			List<Hotel> hotels = hotelRepository.findAll();
			for (Hotel hotel : hotels) {
				result = hotel.getRooms();
				for (Room room : result) {
					for (Room room1 : rooms) {
						if (room.getRoomNo() == room1.getRoomNo()) {
							temp1.add(room);
							temp2.add(room.getHotelRef());
							for (Hotel hotel2 : temp2) {
								hotel2.setRooms(temp1);
							}
						}
					}
				}
			}
			return temp2;
		} else {
			throw new BookingException("Enter valid customer Id");
		}
	}

	@Override
	public List<Hotel> searchByHotelName(String hotelName) throws BookingException {
		if (hotelName != null) {
			return hotelRepository.findAllByHotelName(hotelName);
		} else {
			throw new BookingException("Enter Hotel name");
		}
	}

	@Override
	public List<Hotel> searchHotelsByCity(String city) throws BookingException {
		if (city != null) {
			List<Hotel> hotels = new ArrayList<Hotel>();
			for (Hotel hotel : hotelRepository.findAll()) {
				if (hotel.getAddress().getCity().equalsIgnoreCase(city)) {
					hotels.add(hotel);
				}
			}
			return hotels;
		} else {
			throw new BookingException("Enter City name");
		}
	}

	@Override
	public CustomerCart addToCartByPhNo(int roomId, long phNo) throws BookingException {
		if (roomId != 0 && phNo != 0 && roomRepository.findById(roomId).get().getAvailability()==Availability.NOT_BOOKED) {
				Room room = new Room();
				room = roomRepository.findById(roomId).get();
				Customer customer = new Customer();
				customer = customerRepository.findByCustomerPhNo(phNo);
				CustomerCart cart = new CustomerCart();
				cart = customer.getCart();
				cart.getRooms().add(room);
				room.setCartRef(cart);
				roomRepository.save(room);
				cartRepository.save(cart);
				customerRepository.save(customer);
			return cart;
		} else {
			throw new BookingException("**Invalid Given Data**");
		}
	}

	@Override
	public CustomerCart cancelOrder(long phNo, int roomNo) throws BookingException {
		if (phNo != 0) {
			Customer customer = new Customer();
			List<Room> rooms = new ArrayList<Room>();
			customer = customerRepository.findByCustomerPhNo(phNo);
			CustomerCart cart = new CustomerCart();
			cart = customer.getCart();
			rooms = cart.getRooms();
			if (customer != null && rooms != null) {
				for (Room room : rooms) {
					if (room.getRoomNo() == roomNo) {
						cart.setStatus(CartStatus.NOTBOOKED);
//						cart.setCheckIn(null);
//						cart.setCheckOut(null);
						room.setAvailability(Availability.NOT_BOOKED);
						room.setCartRef(null);
						cart.getRooms().remove(room);
						cartRepository.save(cart);
						roomRepository.save(room);
						customerRepository.save(customer);
					}
				}
				return cart;
			} else {
				throw new BookingException("given id is not present");
			}
		} else {
			throw new BookingException("input cannot be empty");
		}
	}

	@Override
	public Customer createCustomer(Customer customer) throws BookingException {
		CustomerCart cart = new CustomerCart();
		cart.setCustomerPhNo(customer.getCustomerPhNo());
		cart.setStatus(CartStatus.NOTBOOKED);
		cartRepository.save(cart);
		customer.setCart(cart);
		customerRepository.save(customer);
		return customer;
	}

	@Override
	public List<Room> findCartRooms(int hotelId, long custNo) throws BookingException {
		if (hotelId != 0 && custNo != 0L) {
			List<Room> rooms = roomRepository.findCartRooms(hotelId, custNo);
			if (rooms == null) {
				throw new BookingException("There are no rooms Yet");
			}
			return rooms;
		} else {
			throw new BookingException("Enter the valid input");
		}

	}

	@Override
	public CustomerCart findCart(long phNo) throws BookingException {
		if (phNo != 0) {
			CustomerCart cart = customerRepository.findById(phNo).get().getCart();
			return cart;
		} else {
			throw new BookingException("Enter valid input");
		}

	}

	@Override
	public CustomerCart bookRoom(int roomNo, Payment payType, LocalDate checkIn, LocalDate checkOut, long phNo)
			throws BookingException {
		List<Room> rooms = new ArrayList<Room>();
		if (phNo != 0 && checkIn.isAfter(LocalDate.now()) && checkOut.isAfter(LocalDate.now())) {
			Customer customer = new Customer();
			customer = customerRepository.findByCustomerPhNo(phNo);
			CustomerCart cart = new CustomerCart();
			cart = customer.getCart();
			rooms = cart.getRooms();
			for (Room room : rooms) {
				for (LocalDate date = checkIn; date.isBefore(checkOut); date = date.plusDays(1)) {
					if (room.getAvailability() == Availability.NOT_BOOKED && checkIn != LocalDate.now()
							&& room.getRoomNo() == roomNo) {
						room.setAvailability(Availability.BOOKED);
						cart.setStatus(CartStatus.BOOKED);
						cart.setCheckIn(checkIn);
						cart.setCheckOut(checkOut);
						cart.setPaymentType(payType);
					}
				}
			}
			roomRepository.saveAll(rooms);
			cartRepository.save(cart);
			customerRepository.save(customer);
			return cart;
		} else {
			throw new BookingException("Booking Failed");
		}
	}
}
