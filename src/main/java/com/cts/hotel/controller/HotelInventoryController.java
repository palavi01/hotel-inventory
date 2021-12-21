package com.cts.hotel.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cts.hotel.model.FloorModel;
import com.cts.hotel.model.HotelModel;
import com.cts.hotel.model.RoomModel;
import com.cts.hotel.model.RoomTypeModel;
import com.cts.hotel.service.HotelInventoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*")

//This annotation is used to map web requests to Spring Controller methods.
@RequestMapping("v1/hotel")
@Tag(name = "Hotel", description = "Hotel Inventory Management System")
public class HotelInventoryController {

	@Autowired
    private HotelInventoryService hotelInventoryService;
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Operation(summary = "Adding Hotels", 
	 			responses = {@ApiResponse(responseCode = "201", description = "Hotel Added Successfully", content = @Content(schema = @Schema(implementation = String.class))),
	 					     @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))},
	 			requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = HotelModel.class))))
    public Mono<HotelModel> createHotel(@org.springframework.web.bind.annotation.RequestBody @Valid HotelModel hotelModel) {

        return hotelInventoryService.createHotel(hotelModel);

    }
	
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Operation(summary = "Updating Hotels", 
				responses = {@ApiResponse(responseCode = "200", description = "Hotel Updated Successfully", content = @Content(schema = @Schema(implementation = String.class))),
				     @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))},
				requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = HotelModel.class))))
    public Mono<HotelModel> updateHotel(@org.springframework.web.bind.annotation.RequestBody @Valid HotelModel hotelModel) {

        return hotelInventoryService.updateHotel(hotelModel);
    }
	
	@GetMapping(value = "/fetch-rooms", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Operation(summary = "Get a List of Rooms - should return Room Number, Room Type, Floor", 
	responses = {@ApiResponse(responseCode = "200", description = "Get a List of Rooms - should return Room Number, Room Type, Floor", content = @Content(schema = @Schema(implementation = String.class))),
				@ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))})
    public Flux<List<RoomModel>> fetchRooms(@RequestParam(required = true) String hotelId) {

        return hotelInventoryService.fetchRooms(hotelId);
    }
	
	@GetMapping(value = "/fetch-roomTypes", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Operation(summary = "Get a List of Room Types - should return room type, Room description", 
	responses = {@ApiResponse(responseCode = "200", description = "Get a List of Rooms - should return Room Number, Room Type, Floor", content = @Content(schema = @Schema(implementation = String.class))),
				@ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))})
    public Flux<List<RoomTypeModel>> fetchRoomTypes(@RequestParam(required = true) String hotelId) {

		return hotelInventoryService.fetchRoomTypes(hotelId);
    }
	
	@GetMapping(value = "/fetch-floors", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@Operation(summary = "Get a List of Floors - should return floor number, all rooms and respective details belonging to that floor", 
	responses = {@ApiResponse(responseCode = "200", description = "Get a List of Rooms - should return Room Number, Room Type, Floor", content = @Content(schema = @Schema(implementation = String.class))),
				@ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))})
    public Flux<List<FloorModel>> fetchFloors(@RequestParam(required = true) String hotelId) {

        return hotelInventoryService.fetchFloors(hotelId);

    }
}