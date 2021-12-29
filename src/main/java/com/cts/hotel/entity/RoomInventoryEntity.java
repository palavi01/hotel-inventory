package com.cts.hotel.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "room")
public class RoomInventoryEntity {

	@Id 
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