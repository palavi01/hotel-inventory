package com.cts.hotel.entity;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roomType")
public class RoomTypeEntity extends CommonEntity {

	private static final long serialVersionUID = -4003317696410874021L;

	@Id 
	private String roomTypeId;

	private String roomTypeName;
	
	private String description;
	
	private String hotelId;
}