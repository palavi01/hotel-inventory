package com.cts.hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.entity.HotelEntity;

public interface HotelDao extends JpaRepository<HotelEntity, Long> {

}