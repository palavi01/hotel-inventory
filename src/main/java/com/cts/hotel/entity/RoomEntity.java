package com.cts.hotel.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	@JsonProperty("floors")
	private List<FloorEntity> floorEntites;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable
	@JsonProperty("roomTypes")
	private List<RoomTypeEntity> roomTypeEntities;
	
	@Override
	public String toString() {
		return Long.toString(roomId);
	}
}