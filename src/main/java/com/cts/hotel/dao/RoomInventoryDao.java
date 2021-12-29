package com.cts.hotel.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.cts.hotel.entity.RoomInventoryEntity;

import reactor.core.publisher.Flux;

public interface RoomInventoryDao extends ReactiveMongoRepository<RoomInventoryEntity, String> {
	
	@Query("{'hotelId' : ?0, 'roomId' : ?1}")
	Flux<RoomInventoryEntity> getRoomByHotelIdAndRoomId(int hotelId, int roomId);
	
	@Query("{'hotelId' : ?0, 'floorId' : ?1}")
	Flux<RoomInventoryEntity> getRoomsByHotelIdAndFloorId(int hotelId, int floorId);

}
