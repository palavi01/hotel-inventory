package com.cts.hotel.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "floor")
public class FloorEntity extends CommonEntity {

	private static final long serialVersionUID = 9199576141597336829L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "floor_id")
	@JsonProperty("floorId")
	private Long floorId;

	@Column(name = "floor_name", nullable = false, length = 10)
	@JsonProperty("floorName")
	private String floorName;
	
	@ManyToMany(mappedBy = "floorEntities")
	@JsonProperty("hotels")
	private List<HotelEntity> hotelEntities;
	
	@Override
	public String toString() {
		return Long.toString(floorId);
	}
}