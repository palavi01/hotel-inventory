package com.cts.hotel.entity;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "room")
public class RoomEntity extends CommonEntity {

	private static final long serialVersionUID = 3812406002790023373L;

	@Id 
	private String roomId;

	private String roomNumber;
	
	private String hotelId;
	
	private String roomType;
	
	private String floorName;
}