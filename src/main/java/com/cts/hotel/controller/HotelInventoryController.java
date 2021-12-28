package com.cts.hotel.controller;

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
@RequestMapping("v1/hotel")
@Tag(name = "Hotel", description = "Hotel Inventory Management System")
public class HotelInventoryController {

	@Autowired
    private HotelInventoryService hotelInventoryService;
	
	@PostMapping()
	@Operation(summary = "Adding Rooms", 
	 			responses = {@ApiResponse(responseCode = "201", description = "Room Added Successfully", content = @Content(schema = @Schema(implementation = String.class))),
	 					     @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))},
	 			requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = RoomModel.class))))
    public void createRoom(@org.springframework.web.bind.annotation.RequestBody @Valid RoomModel roomModel) {

        hotelInventoryService.createRoom(roomModel);

    }
	
	@PutMapping()
	@Operation(summary = "Updating Rooms", 
				responses = {@ApiResponse(responseCode = "200", description = "Room Updated Successfully", content = @Content(schema = @Schema(implementation = String.class))),
				     @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))},
				requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = RoomModel.class))))
    public void updateRoom(@org.springframework.web.bind.annotation.RequestBody @Valid RoomModel roomModel) {

        hotelInventoryService.updateRoom(roomModel);
    }
	
	@GetMapping(value = "/fetch-rooms", produces = { MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "Get a List of Rooms - should return Room Number, Room Type, Floor", 
	responses = {@ApiResponse(responseCode = "200", description = "Get a List of Rooms - should return Room Number, Room Type, Floor", content = @Content(schema = @Schema(implementation = String.class))),
				@ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))})
    public Flux<RoomModel> fetchRooms(@RequestParam(required = true) String hotelId) {

        return hotelInventoryService.fetchRooms(hotelId);
    }
	
	@GetMapping(value = "/fetch-roomtypes", produces = { MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "Get a List of Room Types - should return room type, Room description", 
	responses = {@ApiResponse(responseCode = "200", description = "Get a List of Rooms - should return Room Number, Room Type, Floor", content = @Content(schema = @Schema(implementation = String.class))),
				@ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))})
    public Flux<RoomTypeModel> fetchRoomTypes(@RequestParam(required = true) String hotelId) {

		return hotelInventoryService.fetchRoomTypes(hotelId);
    }
	
	@GetMapping(value = "/fetch-floors", produces = { MediaType.APPLICATION_JSON_VALUE})
	@Operation(summary = "Get a List of Floors - should return floor number, all rooms and respective details belonging to that floor", 
	responses = {@ApiResponse(responseCode = "200", description = "Get a List of Rooms - should return Room Number, Room Type, Floor", content = @Content(schema = @Schema(implementation = String.class))),
				@ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))})
    public Flux<FloorModel> fetchFloors(@RequestParam(required = true) String hotelId) {

        return hotelInventoryService.fetchFloors(hotelId);

    }
	
	@PostMapping("/add-room-types")
	@Operation(summary = "Adding Room Types", 
	 			responses = {@ApiResponse(responseCode = "201", description = "Room Types Added Successfully", content = @Content(schema = @Schema(implementation = String.class))),
	 					     @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))},
	 			requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = RoomTypeModel.class))))
    public Mono<RoomTypeModel> addRoomTypes(@org.springframework.web.bind.annotation.RequestBody RoomTypeModel roomTypeModel) {

        return hotelInventoryService.addRoomTypes(roomTypeModel);
    }
	
	@PostMapping("/add-floors")
	@Operation(summary = "Adding Floor Values", 
	 			responses = {@ApiResponse(responseCode = "201", description = "Floor Added Successfully", content = @Content(schema = @Schema(implementation = String.class))),
	 					     @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(schema = @Schema(implementation = String.class)))},
	 			requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = FloorModel.class))))
    public Mono<FloorModel> addFloors(@org.springframework.web.bind.annotation.RequestBody FloorModel floorModel) {

        return hotelInventoryService.addFloors(floorModel);
    }
}