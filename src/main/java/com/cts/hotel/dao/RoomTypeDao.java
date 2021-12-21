package com.cts.hotel.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.entity.RoomTypeEntity;

public interface RoomTypeDao extends JpaRepository<RoomTypeEntity, Long> {

	List<RoomTypeEntity> findByStatusAndHotelEntityHotelId(int status, long hotelId);
}