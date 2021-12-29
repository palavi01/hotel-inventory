package com.cts.hotel.service;

import com.cts.hotel.model.FloorModel;
import com.cts.hotel.model.RoomInventoryModel;
import com.cts.hotel.model.RoomModel;
import com.cts.hotel.model.RoomTypeModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HotelInventoryService {

	void createRoom(RoomModel roomModel);

	void updateRoom(RoomModel roomModel);

	Flux<RoomModel> fetchRooms(String hotelId);
	
	Flux<RoomTypeModel> fetchRoomTypes(String hotelId);

	Flux<FloorModel> fetchFloors(String hotelId);

	Mono<RoomTypeModel> addRoomTypes(RoomTypeModel roomTypeModel);

	Mono<FloorModel> addFloors(FloorModel floorModel);

	void createRoomInventory(RoomInventoryModel roomModel);

	Flux<RoomInventoryModel> fetchRoomInventoryByHotelAndFloor(String hotelId, String floorId);
}