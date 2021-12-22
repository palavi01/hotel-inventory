package com.cts.hotel.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.cts.hotel.entity.FloorEntity;

import reactor.core.publisher.Flux;

public interface FloorDao extends ReactiveMongoRepository<FloorEntity, String> {

	Flux<FloorEntity> findByStatusAndHotelId(int status, String hotelId);
}