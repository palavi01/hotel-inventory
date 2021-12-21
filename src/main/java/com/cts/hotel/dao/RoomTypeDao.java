package com.cts.hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.entity.RoomTypeEntity;

public interface RoomTypeDao extends JpaRepository<RoomTypeEntity, Long> {

}