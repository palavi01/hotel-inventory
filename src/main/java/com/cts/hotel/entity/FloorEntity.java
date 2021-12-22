package com.cts.hotel.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "floor")
public class FloorEntity {

	@Id
	private String floorId;

	private String floorName;

	private String hotelId;

	private Integer status;

	private String createdDate;

	private String createdBy;

	private String modifiedDate;

	private String modifiedBy;
}