package com.cts.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.hotel.dao.FloorDao;
import com.cts.hotel.dao.HotelDao;
import com.cts.hotel.dao.RoomDao;
import com.cts.hotel.dao.RoomTypeDao;
import com.cts.hotel.model.FloorModel;
import com.cts.hotel.model.HotelModel;
import com.cts.hotel.model.RoomModel;
import com.cts.hotel.model.RoomTypeModel;
import com.cts.hotel.service.HotelInventoryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HotelInventoryServiceImpl implements HotelInventoryService {

	@Autowired
	private HotelDao hotelDao;
	
	@Autowired
	private FloorDao floorDao;
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private RoomTypeDao roomTypeDao;
	
	@Override
	public Mono<HotelModel> createHotel(HotelModel hotelModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<HotelModel> updateHotel(HotelModel hotelModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<List<RoomModel>> fetchRooms(String hotelId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<List<RoomTypeModel>> fetchRoomTypes(String hotelId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<List<FloorModel>> fetchFloors(String hotelId) {
		// TODO Auto-generated method stub
		return null;
	}

}
