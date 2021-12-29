package com.cts.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.cts.hotel.dao.FloorDao;
import com.cts.hotel.dao.RoomDao;
import com.cts.hotel.dao.RoomInventoryDao;
import com.cts.hotel.dao.RoomTypeDao;
import com.cts.hotel.entity.FloorEntity;
import com.cts.hotel.entity.RoomEntity;
import com.cts.hotel.entity.RoomInventoryEntity;
import com.cts.hotel.entity.RoomTypeEntity;
import com.cts.hotel.helper.Status;
import com.cts.hotel.helper.Util;
import com.cts.hotel.model.FloorModel;
import com.cts.hotel.model.RoomInventoryModel;
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
	
	@Autowired
	private RoomInventoryDao roomInventoryDao;
	
	@Value("${add_room_topic}")
    private String addRoomTopic;
	
	@Value("${add_room_inventory_topic}")
    private String addRoomInventoryTopic;
	
	@Value("${update_room_topic}")
    private String updateRoomTopic;
	
	@Autowired
	private ReactiveKafkaProducerTemplate<String, RoomModel> addRoomKafkaProducerTemplate;
	
	@Autowired
	private ReactiveKafkaProducerTemplate<String, RoomInventoryModel> addRoomInventoryKafkaProducerTemplate;
	
	@Autowired
	private ReactiveKafkaProducerTemplate<String, RoomModel> updateRoomKafkaProducerTemplate;
	
	@Value("${not.found}")
	private String notFound;
	
	@Autowired
	private KafkaConsumer kafkaConsumer;
	
	@Override
	public void createRoom(RoomModel roomModel) {
		
		roomModel.setCreatedBy("1");
		roomModel.setCreatedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
		roomModel.setStatus(Status.ACTIVE.ordinal());
		
		addRoomKafkaProducerTemplate.send(addRoomTopic, roomModel)
        .doOnSuccess(senderResult -> System.out.println("sent ==>> "+ roomModel+ " offset ==>> "+ senderResult.recordMetadata().offset()))
        .subscribe();
		kafkaConsumer.addRoom().subscribe();
	}

	
	public void updateRoom(RoomModel roomModel) {
		
		roomModel.setModifiedBy("1");
		roomModel.setModifiedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
		updateRoomKafkaProducerTemplate.send(updateRoomTopic, roomModel)
		        .doOnSuccess(senderResult -> System.out.println("sent ==>> "+ roomModel+ " offset ==>> "+ senderResult.recordMetadata().offset()))
		        .subscribe();
		kafkaConsumer.updateRoom().subscribe();
	}
	
	@Override
	public void createRoomInventory(RoomInventoryModel roomInventoryModel) {
		
		roomInventoryModel.setCreatedBy("1");
		roomInventoryModel.setCreatedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
		roomInventoryModel.setStatus(Status.ACTIVE.ordinal());
		
		addRoomInventoryKafkaProducerTemplate.send(addRoomInventoryTopic, roomInventoryModel)
        .doOnSuccess(senderResult -> System.out.println("sent ==>> "+ roomInventoryModel+ " offset ==>> "+ senderResult.recordMetadata().offset()))
        .subscribe();
		kafkaConsumer.addRoomInventory().subscribe();
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


	@Override
	public Flux<RoomInventoryModel> fetchRoomInventoryByHotelAndFloor(String hotelId, String floorId) {
		
		Flux<RoomInventoryEntity> roomInventoryEntities = roomInventoryDao.getRoomsByHotelIdAndFloorId(hotelId, floorId);
		return roomInventoryEntities.map(re -> util.transform(re, RoomInventoryModel.class)).log();
	}


	@Override
	public Flux<RoomInventoryModel> fetchRoomInventoryByHotelAndRoom(String hotelId, String roomId) {
		
		Flux<RoomInventoryEntity> roomInventoryEntities = roomInventoryDao.getRoomByHotelIdAndRoomId(hotelId, roomId);
		return roomInventoryEntities.map(re -> util.transform(re, RoomInventoryModel.class)).log();
	}
}