package com.cts.hotel.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FloorModel extends CommonModel {

	@JsonProperty("floorId")
	private String floorId;
	
	@JsonProperty("floorName")
	private String floorName;
	
	@JsonProperty("rooms")
	private Set<RoomModel> roomModels;
}