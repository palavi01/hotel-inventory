package com.cts.hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.entity.FloorEntity;

import reactor.core.publisher.Flux;

public interface FloorDao extends JpaRepository<FloorEntity, Long> {

	Flux<FloorEntity> findByStatusAndHotelId(int status, long hotelId);
}