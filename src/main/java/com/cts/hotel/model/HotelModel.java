package com.cts.hotel.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HotelModel extends CommonModel {

	@JsonProperty("hotelId")
	private String hotelId;
	
	@NotBlank(message = "{hotelName.null.message}")
	@JsonProperty("hotelName")
	private String hotelName;
	
	@NotBlank(message = "{addressLine1.null.message}")
    @JsonProperty("addressLine1")
    private String addressLine1;

    @JsonProperty("addressLine2")
    private String addressLine2;

    @JsonProperty("landmark")
    private String landmark;

    @JsonProperty("latitude")
    private String latitude;

    @JsonProperty("longitude")
    private String longitude;

    @NotBlank(message = "{mobileNo.null.message}")
    @JsonProperty("mobileNo")
    private String mobileNo;

    @NotBlank(message = "{emailId.null.message}")
    @JsonProperty("emailId")
    private String emailId;

    @JsonProperty("website")
    private String website;

    @NotBlank(message = "{pinCode.null.message}")
    @JsonProperty("pinCode")
    private String pinCode;
    
    @JsonProperty("floors")
    private List<@NotBlank(message = "{floor.null.message}") FloorModel> floorModels;
}