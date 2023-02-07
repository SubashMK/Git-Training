package com.stg.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admin")
public class Admin {

	@Id
	@Column(name = "admin_phno",unique = true)
	private long adminPhNo;
	@Column(name = "admin_name")
	private String adminName;
	@Column(name = "admin_password",nullable = false)
	private String adminPassword;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "adminHotelRef")
	@JsonManagedReference
	private List<Hotel> hotels;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "adminRef")
	@JsonManagedReference
	private List<Customer> customers;
	
}
