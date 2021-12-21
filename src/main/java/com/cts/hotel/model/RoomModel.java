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
	
	@JsonProperty("hotel")
	private HotelModel hotelModel;
	
	@JsonProperty("roomType")
	private RoomTypeModel roomTypeModel;
	
	@JsonProperty("floor")
	private FloorModel floorModel;
}