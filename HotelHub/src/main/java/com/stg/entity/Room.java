package com.stg.entity;

import java.io.Serializable;

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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "room_no")
	private int roomNo;
	@Column(name = "door_no")
	private int doorNo;
	@Enumerated(EnumType.STRING)
	@Column(name = "room_type")
	private RoomType roomType;
	@Enumerated(EnumType.STRING)
	@Column(name = "room_size")
	private RoomSize roomSize;
	@Enumerated(EnumType.STRING)
	@Column(name = "availability")
	private Availability availability;
	@Column(name = "room_cost")
	private float cost;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cart_id",nullable = true)
	@JsonBackReference
	private CustomerCart cartRef;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hotel_no_fk",referencedColumnName = "hotel_no",nullable = true)
	@JsonBackReference
	private Hotel hotelRef;

}
