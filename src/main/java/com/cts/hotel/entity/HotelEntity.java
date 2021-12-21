package com.cts.hotel.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "hotel")
public class HotelEntity extends CommonEntity {

	private static final long serialVersionUID = -2954435435672636844L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_id")
	@JsonProperty("hotelId")
	private Long hotelId;

	@Column(name = "hotel_name", nullable = false, length = 100)
	@JsonProperty("hotelName")
	private String hotelName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hotelEntity", cascade = { CascadeType.ALL })
	@JsonProperty("floors")
	private List<FloorEntity> floorEntities;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hotelEntity", cascade = { CascadeType.ALL })
	@JsonProperty("roomTypes")
	private List<RoomTypeEntity> roomTypeEntities;

	@Override
	public String toString() {
		return Long.toString(hotelId);
	}
}