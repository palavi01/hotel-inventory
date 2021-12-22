package com.cts.hotel.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class RoomTypeModel {

	private String roomTypeId;

	private String roomTypeName;
	
	private String description;
	
	private String hotelId;
	
	private Integer status;

	private String createdDate;

	private String createdBy;

	private String modifiedDate;

	private String modifiedBy;
}