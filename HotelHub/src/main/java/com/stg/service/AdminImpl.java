package com.stg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stg.entity.Admin;
import com.stg.entity.Customer;
import com.stg.entity.CustomerCart;
import com.stg.entity.Hotel;
import com.stg.entity.HotelAddress;
import com.stg.entity.Rating;
import com.stg.exception.BookingException;
import com.stg.repository.AdminRepository;
import com.stg.repository.CustomerCartRepository;
import com.stg.repository.CustomerRepository;
import com.stg.repository.HotelAddressRepository;
import com.stg.repository.HotelRepository;

@Service
public class AdminImpl implements AdminInterface {

	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerCartRepository cartRepository;
	@Autowired
	private HotelAddressRepository addressRepository;

	
	@Override
	public Admin findByAdminNo(long phNo) throws BookingException {
		Admin temp =null;
		Admin admin = adminRepository.findByAdminPhNo(phNo);
		if(admin.getAdminPhNo()==phNo) {
			temp = admin;
		}else {
			throw new BookingException("this phNo is not present");
		}
		return temp;
	}
	
	@Override
	public Hotel createHotel(long adminId, String password, String hotelName, Rating rating, int pincode, String city,
			String state) throws BookingException {
		if (adminId != 0 && password != null && hotelName != null && rating != null && city != null && state != null
				&& pincode != 0) {
			Admin admin = new Admin();
			Hotel hotel = new Hotel();
			admin = adminRepository.findById(adminId).get();
			if (admin != null && password.equals(admin.getAdminPassword())) {
				hotel.setHotelName(hotelName);
				hotel.setRating(rating);
				HotelAddress address = new HotelAddress();
				address.setCity(city);
				address.setState(state);
				address.setPincode(pincode);
				hotel.setAddress(address);
				admin.getHotels().add(hotel);
				adminRepository.save(admin);
				hotelRepository.save(hotel);
				return hotel;
			} else {
				throw new BookingException("wrong admin credentials");
			}
		} else {
			throw new BookingException("Give the full details");
		}
	}

	@Override
	public List<Hotel> deleteHotelByHotelId(int hotelId) throws BookingException {
		if (hotelId != 0) {
			Hotel hotel = new Hotel();
			hotel = hotelRepository.findById(hotelId).get();
			hotelRepository.deleteById(hotel.getHotelNo());
			addressRepository.delete(hotel.getAddress());

			return hotelRepository.findAll();
		} else {
			throw new BookingException("Invalid HotelId");
		}
	}

	@Override
	public List<Hotel> showAllHotels() throws BookingException {
		List<Hotel> hotels = new ArrayList<Hotel>();
				hotels =hotelRepository.findAll();
		if (hotels != null) {
			return hotels;
		} else {
			throw new BookingException("No hotels are present");
		}
	}

	@Override
	public Admin createAdmin(long phNo, String name, String password) throws BookingException {
		if (phNo != 0 && name != null && password != null) {
			Admin admin = new Admin();
			if (String.valueOf(password).length() > 4) {
				admin.setAdminName(name);
				admin.setAdminPhNo(phNo);
				admin.setAdminPassword(password);
				adminRepository.save(admin);
			} else {
				throw new BookingException("password length must be greater than 4");
			}
			return admin;
		} else {
			throw new BookingException("Enter Value should not be empty");
		}
	}

	@Override
	public Admin updateAdminById(long adminId, String newPassword, String oldPassword) throws BookingException {
		if (adminId != 0 && newPassword != null) {
			Admin admin = new Admin();
			admin = adminRepository.findById(adminId).get();
			if (admin.getAdminPassword().equals(oldPassword) && admin != null) {
				admin.setAdminPassword(newPassword);
				return adminRepository.save(admin);
			} else {
				throw new BookingException("Old Password doesn't match");
			}
		} else {
			throw new BookingException("Give appropriate input");
		}
	}

	@Override
	public Customer createNewCustomer(String newName, long phNo, String email, int age) throws BookingException {
		if (newName != null && phNo != 0 && email != null && customerRepository.findByCustomerPhNo(phNo) == null) {
			Customer customer = new Customer();
			customer.setCustomerName(newName);
			customer.setCustomerPhNo(phNo);
			customer.setEmail(email);
			customer.setAge(age);
			CustomerCart cart = new CustomerCart();
			cart.setCustomerPhNo(phNo);
			cartRepository.save(cart);
			customer.setCart(cart);
			return customerRepository.save(customer);
		} else {
			throw new BookingException("invalid input or User already exist");
		}
	}

	@Override
	public Customer findCustomerByPhNo(long phNo) throws BookingException {
		if (phNo != 0L) {
			return customerRepository.findByCustomerPhNo(phNo);
		} else {
			throw new BookingException("Phone No must be entered");
		}
	}

	@Override
	public List<Customer> readAllCustomer() throws BookingException {
		return customerRepository.findAll();
	}

}
