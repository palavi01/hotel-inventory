package com.cts.hotel.service;

import java.util.List;

import com.cts.hotel.model.FloorModel;
import com.cts.hotel.model.HotelModel;
import com.cts.hotel.model.RoomModel;
import com.cts.hotel.model.RoomTypeModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HotelInventoryService {

	Mono<HotelModel> createHotel(HotelModel hotelModel);

	Mono<HotelModel> updateHotel(HotelModel hotelModel);

	Flux<List<RoomModel>> fetchRooms(String hotelId);
	
	Flux<List<RoomTypeModel>> fetchRoomTypes(String hotelId);

	Flux<List<FloorModel>> fetchFloors(String hotelId);
}