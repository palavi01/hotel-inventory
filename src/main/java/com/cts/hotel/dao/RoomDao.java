package com.cts.hotel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.entity.RoomEntity;

public interface RoomDao extends JpaRepository<RoomEntity, Long> {

	List<RoomEntity> findByStatusAndHotelEntityHotelId(int status, long hotelId);
}