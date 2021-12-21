package com.cts.hotel.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.hotel.entity.FloorEntity;

public interface FloorDao extends JpaRepository<FloorEntity, Long> {

}