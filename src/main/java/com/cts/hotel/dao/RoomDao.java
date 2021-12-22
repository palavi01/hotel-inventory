package com.cts.hotel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.entity.RoomEntity;

import reactor.core.publisher.Flux;

public interface RoomDao extends JpaRepository<RoomEntity, Long> {

	Flux<List<RoomEntity>> findByStatusAndHotelId(int status, long hotelId);
}