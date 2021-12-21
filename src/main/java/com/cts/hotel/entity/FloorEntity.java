package com.cts.hotel.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "floor_details")
public class FloorEntity extends CommonEntity {

	private static final long serialVersionUID = 9199576141597336829L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "floor_id")
	@JsonProperty("floorId")
	private Long floorId;

	@Column(name = "floor_name", nullable = false, length = 50)
	@JsonProperty("floorName")
	private String floorName;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "hotel_id", nullable = false)
	@JsonProperty("hotel")
	private HotelEntity hotelEntity;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "floorEntity", cascade = { CascadeType.ALL })
	@JsonProperty("rooms")
	private List<RoomEntity> roomEntities;
	
	@Override
	public String toString() {
		return Long.toString(floorId);
	}
}