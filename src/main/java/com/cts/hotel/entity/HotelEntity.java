package com.cts.hotel.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hotel")
public class HotelEntity {

	@Id
	private String hotelId;

	private String hotelName;
	
	private Integer status;

	private String createdDate;

	private String createdBy;

	private String modifiedDate;

	private String modifiedBy;
}