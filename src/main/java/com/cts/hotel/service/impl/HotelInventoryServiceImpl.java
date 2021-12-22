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
	
	@Value("${not.found}")
	private String notFound;
	
	@Override
	public Mono<RoomModel> createRoom(RoomModel roomModel) {
		
		//kafkaTemplate.send(addRoomTopic, roomModel);
		roomModel.setCreatedBy("1");
		roomModel.setCreatedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
		roomModel.setStatus(Status.ACTIVE.ordinal());
		RoomEntity roomEntity = util.transform(roomModel, RoomEntity.class);
		System.err.println("roomEntity ==>> "+roomEntity);
		return roomDao.save(roomEntity).log().map(re -> util.transform(re, RoomModel.class));
		//return Mono.just(roomModel);
	}

	@Override
	public Mono<RoomModel> updateRoom(RoomModel roomModel) {
		
		//kafkaTemplate.send(updateRoomTopic, roomModel);
		return roomDao.findById(roomModel.getRoomId()).flatMap(roomEntity -> {
			roomEntity.setModifiedBy("1");
			roomEntity.setModifiedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
			roomEntity.setStatus(roomModel.getStatus());
			roomEntity.setRoomNumber(roomModel.getRoomNumber());
			roomEntity.setRoomType(roomModel.getRoomType());
			roomEntity.setFloorName(roomModel.getFloorName());
			roomEntity.setHotelId(roomModel.getHotelId());
			System.err.println("roomEntity ==>> "+roomEntity);
			return roomDao.save(roomEntity).log().map(re -> util.transform(re, RoomModel.class));
		});
	}
	
	@Override
	public Flux<RoomModel> fetchRooms(String hotelId) {
		
		Flux<RoomEntity> roomEntities = roomDao.findByStatusAndHotelId(Status.ACTIVE.ordinal(), hotelId);
		return roomEntities.map(re -> util.transform(re, RoomModel.class)).log();
	}

	@Override
	public Flux<RoomTypeModel> fetchRoomTypes(String hotelId) {
		
		Flux<RoomTypeEntity> roomTypeEntities = roomTypeDao.findByStatusAndHotelId(Status.ACTIVE.ordinal(), hotelId);
		return roomTypeEntities.map(rte -> util.transform(rte, RoomTypeModel.class));
	}

	@Override
	public Flux<FloorModel> fetchFloors(String hotelId) {
		
		Flux<FloorEntity> floorEntities = floorDao.findByStatusAndHotelId(Status.ACTIVE.ordinal(), hotelId);
		return floorEntities.map(fe -> util.transform(fe, FloorModel.class));
	}

	@Override
	public Mono<RoomTypeModel> addRoomTypes(RoomTypeModel roomTypeModel) {
		
		roomTypeModel.setCreatedBy("1");
		roomTypeModel.setCreatedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
		roomTypeModel.setStatus(Status.ACTIVE.ordinal());
		RoomTypeEntity roomTypeEntity = util.transform(roomTypeModel, RoomTypeEntity.class);
		System.err.println("roomTypeEntity ==>> "+roomTypeEntity);
		return roomTypeDao.save(roomTypeEntity).log().map(re -> util.transform(re, RoomTypeModel.class));
	}

	@Override
	public Mono<FloorModel> addFloors(FloorModel floorModel) {
		
		floorModel.setCreatedBy("1");
		floorModel.setCreatedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
		floorModel.setStatus(Status.ACTIVE.ordinal());
		FloorEntity floorEntity = util.transform(floorModel, FloorEntity.class);
		System.err.println("floorEntity ==>> "+floorEntity);
		return floorDao.save(floorEntity).log().map(re -> util.transform(re, FloorModel.class));
	}
}