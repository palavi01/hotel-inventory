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
@Table(name = "room_type")
public class RoomTypeEntity extends CommonEntity {

	private static final long serialVersionUID = -4003317696410874021L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roomtype_id")
	@JsonProperty("roomTypeId")
	private Long roomTypeId;

	@Column(name = "roomtype_name", nullable = false, length = 50)
	@JsonProperty("roomTypeName")
	private String roomTypeName;
	
	@Column(name = "description")
	@JsonProperty("description")
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "hotel_id", nullable = false)
	@JsonProperty("hotel")
	private HotelEntity hotelEntity;
	
	@Override
	public String toString() {
		return Long.toString(roomTypeId);
	}
}