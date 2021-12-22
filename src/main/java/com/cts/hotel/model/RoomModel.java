package com.cts.hotel.model;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
@Validated
public class RoomModel {

	private String roomId;

	@NotBlank(message = "{room.number.null.message}")
	private String roomNumber;
	
	@NotBlank(message = "{hotel.null.message}")
	private String hotelId;
	
	@NotBlank(message = "{room.type.null.message}")
	private String roomType;
	
	@NotBlank(message = "{floor.null.message}")
	private String floorName;
	
	private Integer status;

	private String createdDate;

	private String createdBy;

	private String modifiedDate;

	private String modifiedBy;
}