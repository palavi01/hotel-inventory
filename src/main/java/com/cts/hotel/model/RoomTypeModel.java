package com.cts.hotel.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomTypeModel extends CommonModel {

	@JsonProperty("roomTypeId")
	private String roomTypeId;
	
	@JsonProperty("roomTypeName")
	private String roomTypeName;
	
	@JsonProperty("description")
	private String description;
}