package com.cts.hotel.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
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