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
public class FloorEntity extends CommonEntity {

	private static final long serialVersionUID = 9199576141597336829L;

	@Id 
	private String floorId;

	private String floorName;
	
	private String hotelId;
}