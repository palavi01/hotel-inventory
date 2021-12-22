package com.cts.hotel.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.cts.hotel.entity.HotelEntity;

public interface HotelDao extends ReactiveMongoRepository<HotelEntity, String> {

}