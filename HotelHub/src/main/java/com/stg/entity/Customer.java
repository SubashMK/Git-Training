package com.stg.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_phno", unique = true,nullable = false)
	private long customerPhNo;
	@Column(length = 20, name = "customer_name")
	private String customerName;
	@Column(name = "customer_age", nullable = false)
	private int age;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(length = 20, name = "customer_email")
	private String email;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id", nullable = true)
	private CustomerCart cart;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_no",nullable = true)
	@JsonBackReference
	private Admin adminRef;
	
}
