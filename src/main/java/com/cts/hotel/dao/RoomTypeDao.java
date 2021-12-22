package com.cts.hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.entity.RoomTypeEntity;

import reactor.core.publisher.Flux;

public interface RoomTypeDao extends JpaRepository<RoomTypeEntity, Long> {

	Flux<RoomTypeEntity> findByStatusAndHotelId(int status, long hotelId);
}
