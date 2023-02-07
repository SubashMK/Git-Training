package com.stg.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "hotel_no")
	private int hotelNo;
	@Column(name = "hotel_name", length = 20)
	private String hotelName;
	@Column(name = "password",length = 20)
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(name = "rating")
	private Rating rating;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "hotelRef")
	@JsonManagedReference
	private List<Room> rooms;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id",nullable = false)
	private HotelAddress address;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_no",nullable = true)
	@JsonBackReference
	private Admin adminHotelRef;
	
}
