package com.stg.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.util.FastByteArrayOutputStream;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_cart")
public class CustomerCart implements Serializable{



//	private int cartId;
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "customer_phno",nullable = false)
//	@JsonBackReference(value = "customerRef")

//	

	@Id
	@Column(name = "cart_id",nullable = false)
	private long customerPhNo;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cartRef")
	@JsonManagedReference
	private List<Room> rooms;
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_type",length = 20)
	private Payment paymentType;
	@Column(name = "check_in",nullable = true)
//	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate checkIn;
//	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "check_out",nullable = true)
	private LocalDate checkOut;
	@Column(name = "total_cost" , nullable = true)
	private float totalCost;
	@Enumerated(EnumType.STRING)
	@Column(name = "cart_status",length = 20 , nullable = true)
	private CartStatus status;
}
