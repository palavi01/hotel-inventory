package com.cts.hotel.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "hotel")
public class HotelEntity extends CommonEntity {

	private static final long serialVersionUID = -2954435435672636844L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_id")
	@JsonProperty("hotelId")
	private Long hotelId;

	@Column(name = "hotel_name", nullable = false, length = 10)
	@JsonProperty("hotelName")
	private String hotelName;
	
	@Column(name = "address_line1", nullable = false, length = 50)
	@JsonProperty("addressLine1")
	private String addressLine1;

	@Column(name = "address_line2")
	@JsonProperty("addressLine2")
	private String addressLine2;
	
	@Column(name = "landmark")
	@JsonProperty("landmark")
	private String landmark;

	@Column(name = "latitude")
	@JsonProperty("latitude")
	private String latitude;

	@Column(name = "longitude")
	@JsonProperty("longitude")
	private String longitude;

	@Column(name = "mobile_no", nullable = false, length = 10)
	@JsonProperty("mobileNo")
	private String mobileNo;

	@Column(name = "email_id", nullable = false, length = 50)
	@JsonProperty("emailId")
	private String emailId;
	
	@Column(name = "website", length = 50)
    @JsonProperty("website")
    private String website;

	@Column(name = "pinCode", nullable = false, length = 7)
    @JsonProperty("pinCode")
    private String pinCode;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinTable
	@JsonProperty("floors")
	private List<FloorEntity> floorEntities;

	@Override
	public String toString() {
		return Long.toString(hotelId);
	}
}