package com.cts.hotel.service;

import com.cts.hotel.model.FloorModel;
import com.cts.hotel.model.RoomModel;
import com.cts.hotel.model.RoomTypeModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HotelInventoryService {

	Mono<RoomModel> createRoom(RoomModel roomModel);

	Mono<RoomModel> updateRoom(RoomModel roomModel);

	Flux<RoomModel> fetchRooms(String hotelId);
	
	Flux<RoomTypeModel> fetchRoomTypes(String hotelId);

	Flux<FloorModel> fetchFloors(String hotelId);
}