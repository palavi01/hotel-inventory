package com.cts.hotel.model;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@Validated
public class RoomInventoryModel {

	private String roomId;

	private String roomNumber;
	
	private String hotelId;
	
	private String roomType;
	
	private String floorId;
	
	private String floorName;
	
	private Integer status;

	private String createdDate;

	private String createdBy;

	private String modifiedDate;

	private String modifiedBy;
}
