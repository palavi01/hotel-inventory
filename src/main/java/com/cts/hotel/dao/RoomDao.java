package com.cts.hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.entity.RoomEntity;

import reactor.core.publisher.Flux;

public interface RoomDao extends JpaRepository<RoomEntity, Long> {

	Flux<RoomEntity> findByStatusAndHotelId(int status, long hotelId);
}