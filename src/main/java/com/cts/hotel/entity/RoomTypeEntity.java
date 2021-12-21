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
@Table(name = "room_type")
public class RoomTypeEntity extends CommonEntity {

	private static final long serialVersionUID = -4003317696410874021L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roomType_id")
	@JsonProperty("roomTypeId")
	private Long roomTypeId;

	@Column(name = "roomType_name")
	@JsonProperty("roomTypeName")
	private String roomTypeName;
	
	@Column(name = "description")
	@JsonProperty("description")
	private String description;
	
	@ManyToMany(mappedBy = "roomTypeEntities")
	@JsonProperty("rooms")
	private List<RoomEntity> roomEntities;
	
	@Override
	public String toString() {
		return Long.toString(roomTypeId);
	}
}
