package com.cts.hotel.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "room")
public class RoomEntity extends CommonEntity {

	private static final long serialVersionUID = 3812406002790023373L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	@JsonProperty("roomId")
	private Long roomId;

	@Column(name = "room_number", nullable = false, length = 10)
	@JsonProperty("roomNumber")
	private String roomNumber;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "hotel_id", nullable = false)
	@JsonProperty("hotel")
	private HotelEntity hotelEntity;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "roomtype_id", nullable = false)
	@JsonProperty("roomType")
	private RoomTypeEntity roomTypeEntity;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "floor_id", nullable = false)
	@JsonProperty("floor")
	private FloorEntity floorEntity;
	
	@Override
	public String toString() {
		return Long.toString(roomId);
	}
}