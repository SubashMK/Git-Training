package com.stg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotel_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelAddress implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "address_id")
	private int addressId;
	@Column(name = "pincode",length = 20)
	private long pincode;
	@Column(name = "city",length = 20)
	private String city;
	@Column(name = "state",length = 20)
	private String state;
	
	
	
}
