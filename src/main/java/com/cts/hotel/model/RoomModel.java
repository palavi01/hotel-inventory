package com.cts.hotel.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RoomModel {

	private String roomId;

	private String roomNumber;
	
	private String hotelId;
	
	private String roomType;
	
	private String floorName;
	
	private Integer status;

	private String createdDate;

	private String createdBy;

	private String modifiedDate;

	private String modifiedBy;
}