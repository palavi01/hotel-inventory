package com.cts.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.cts.hotel.dao.FloorDao;
import com.cts.hotel.dao.RoomDao;
import com.cts.hotel.dao.RoomTypeDao;
import com.cts.hotel.entity.FloorEntity;
import com.cts.hotel.entity.RoomEntity;
import com.cts.hotel.entity.RoomTypeEntity;
import com.cts.hotel.helper.Status;
import com.cts.hotel.helper.Util;
import com.cts.hotel.model.FloorModel;
import com.cts.hotel.model.RoomModel;
import com.cts.hotel.model.RoomTypeModel;
import com.cts.hotel.service.HotelInventoryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HotelInventoryServiceImpl implements HotelInventoryService {

	@Autowired
	private Util util;
	
	@Autowired
	private FloorDao floorDao;
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private RoomTypeDao roomTypeDao;
	
	@Value("${add_room_topic}")
    private String addRoomTopic;
	
	@Value("${update_room_topic}")
    private String updateRoomTopic;
	
	@Autowired
	private KafkaTemplate<String, RoomModel> kafkaTemplate;
	
	@Override
	public Mono<RoomModel> createRoom(RoomModel roomModel) {
		
		kafkaTemplate.send(addRoomTopic, roomModel);
		return Mono.just(roomModel);
	}

	@Override
	public Mono<RoomModel> updateRoom(RoomModel roomModel) {
		
		kafkaTemplate.send(updateRoomTopic, roomModel);
		return Mono.just(roomModel);
	}
	
	@Override
	public Flux<RoomModel> fetchRooms(String hotelId) {
		
		Flux<RoomEntity> roomEntities = roomDao.findByStatusAndHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		return roomEntities.map(re -> util.transform(re, RoomModel.class));
	}

	@Override
	public Flux<RoomTypeModel> fetchRoomTypes(String hotelId) {
		
		Flux<RoomTypeEntity> roomTypeEntities = roomTypeDao.findByStatusAndHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		return roomTypeEntities.map(rte -> util.transform(rte, RoomTypeModel.class));
	}

	@Override
	public Flux<FloorModel> fetchFloors(String hotelId) {
		
		Flux<FloorEntity> floorEntities = floorDao.findByStatusAndHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		return floorEntities.map(fe -> util.transform(fe, FloorModel.class));
	}
}