package com.cts.hotel.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.cts.hotel.entity.RoomTypeEntity;

import reactor.core.publisher.Flux;

public interface RoomTypeDao extends ReactiveMongoRepository<RoomTypeEntity, String> {

	Flux<RoomTypeEntity> findByStatusAndHotelId(int status, String hotelId);
}
