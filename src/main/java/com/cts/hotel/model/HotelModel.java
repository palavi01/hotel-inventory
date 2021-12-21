package com.cts.hotel.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HotelModel extends CommonModel {

	@JsonProperty("hotelId")
	private String hotelId;
	
	@JsonProperty("hotelName")
	private String hotelName;
	
    @JsonProperty("floors")
	private List<FloorModel> floorModels;
	
	@JsonProperty("roomTypes")
	private List<RoomTypeModel> roomTypeModels;
}