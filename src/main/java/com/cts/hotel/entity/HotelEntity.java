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
public class HotelEntity extends CommonEntity {

	private static final long serialVersionUID = -2954435435672636844L;

	@Id
	private String hotelId;

	private String hotelName;
}