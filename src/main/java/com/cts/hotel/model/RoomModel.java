package com.cts.hotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomModel extends CommonModel {

	@JsonProperty("roomId")
	private String roomId;
	
	@JsonProperty("roomNumber")
	private String roomNumber;
	
	@JsonProperty("roomTypeName")
	private String roomTypeName; 
	
	@JsonProperty("floorName")
	private String floorName;
	
	@JsonProperty("roomTypeId")
	private String roomTypeId; 
}