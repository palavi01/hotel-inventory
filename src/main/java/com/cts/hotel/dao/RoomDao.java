package com.cts.hotel.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.cts.hotel.entity.RoomEntity;

import reactor.core.publisher.Flux;

public interface RoomDao extends ReactiveMongoRepository<RoomEntity, String> {

	Flux<RoomEntity> findByStatusAndHotelId(int status, String hotelId);
}