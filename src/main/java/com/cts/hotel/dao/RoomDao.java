package com.cts.hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.entity.RoomEntity;

public interface RoomDao extends JpaRepository<RoomEntity, Long> {

}