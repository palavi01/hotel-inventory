package com.cts.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.cts.hotel.dao.FloorDao;
import com.cts.hotel.dao.HotelDao;
import com.cts.hotel.dao.RoomDao;
import com.cts.hotel.dao.RoomTypeDao;
import com.cts.hotel.entity.FloorEntity;
import com.cts.hotel.entity.HotelEntity;
import com.cts.hotel.entity.RoomEntity;
import com.cts.hotel.entity.RoomTypeEntity;
import com.cts.hotel.exception.DataNotFoundException;
import com.cts.hotel.helper.Status;
import com.cts.hotel.helper.Util;
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
	private Util util;
	
	@Autowired
	private HotelDao hotelDao;
	
	@Autowired
	private FloorDao floorDao;
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private RoomTypeDao roomTypeDao;
	
	@Autowired
	private KafkaTemplate<String, RoomModel> kafkaTemplate;
	
	@Override
	public Mono<RoomModel> createRoom(RoomModel roomModel) {
		
		roomModel.setCreatedBy(1L);
		roomModel.setCreatedDate(util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
        roomModel.setStatus(Status.ACTIVE.ordinal());
        RoomEntity roomEntity = util.transform(roomModel, RoomEntity.class);
        Optional<RoomTypeEntity> roomTypeEntityOptional = roomTypeDao.findById(Long.valueOf(roomModel.getRoomTypeModel().getRoomTypeId()));
        if (roomTypeEntityOptional.isPresent()) {
            roomEntity.setRoomTypeEntity(roomTypeEntityOptional.get());
        } else {
            throw new DataNotFoundException("Invalid Room Type");
        }
        
        Optional<HotelEntity> hotelEntityOptional = hotelDao.findById(Long.valueOf(roomModel.getHotelModel().getHotelId()));
        if (hotelEntityOptional.isPresent()) {
            roomEntity.setHotelEntity(hotelEntityOptional.get());
        } else {
            throw new DataNotFoundException("Invalid Hotel");
        }
        
        Optional<FloorEntity> floorEntityOptional = floorDao.findById(Long.valueOf(roomModel.getFloorModel().getFloorId()));
        if (floorEntityOptional.isPresent()) {
            roomEntity.setFloorEntity(floorEntityOptional.get());
        } else {
            throw new DataNotFoundException("Invalid Floor");
        }
        
        roomDao.save(roomEntity);
        roomModel = util.transform(roomEntity, RoomModel.class);
        
		return Mono.just(roomModel);
	}

	@Override
	public Mono<RoomModel> updateRoom(RoomModel roomModel) {
		
		Optional<RoomEntity> roomEntityOptional = roomDao.findById(Long.valueOf(roomModel.getRoomId()));
		if (roomEntityOptional.isPresent()) {
			RoomEntity roomEntity = roomEntityOptional.get();
			roomModel.setModifiedBy(1L);
			roomModel.setModifiedDate(util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
	        roomModel.setStatus(roomModel.getStatus());
	        roomEntity = util.transform(roomModel, RoomEntity.class);
	        Optional<RoomTypeEntity> roomTypeEntityOptional = roomTypeDao.findById(Long.valueOf(roomModel.getRoomTypeModel().getRoomTypeId()));
	        if (roomTypeEntityOptional.isPresent()) {
	            roomEntity.setRoomTypeEntity(roomTypeEntityOptional.get());
	        } else {
	            throw new DataNotFoundException("Invalid Room Type");
	        }
	        
	        Optional<HotelEntity> hotelEntityOptional = hotelDao.findById(Long.valueOf(roomModel.getHotelModel().getHotelId()));
	        if (hotelEntityOptional.isPresent()) {
	            roomEntity.setHotelEntity(hotelEntityOptional.get());
	        } else {
	            throw new DataNotFoundException("Invalid Hotel");
	        }
	        
	        Optional<FloorEntity> floorEntityOptional = floorDao.findById(Long.valueOf(roomModel.getFloorModel().getFloorId()));
	        if (floorEntityOptional.isPresent()) {
	            roomEntity.setFloorEntity(floorEntityOptional.get());
	        } else {
	            throw new DataNotFoundException("Invalid Floor");
	        }
	        
	        roomDao.save(roomEntity);
	        roomModel = util.transform(roomEntity, RoomModel.class);
        } else {
            throw new DataNotFoundException("Invalid Room Id");
        }
        
		return Mono.just(roomModel);
	}

	@Override
	public Flux<List<RoomModel>> fetchRooms(String hotelId) {
		
		List<RoomEntity> roomEntities = roomDao.findByStatusAndHotelEntityHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		List<RoomModel> roomModels = new ArrayList<>();
		roomEntities.forEach(re -> {
			RoomModel roomModel = new RoomModel();
			roomModel = util.transform(re, RoomModel.class);
			roomModel.setFloorModel(util.transform(re.getFloorEntity(), FloorModel.class));
			roomModel.setHotelModel(util.transform(re.getHotelEntity(), HotelModel.class));
			roomModel.setRoomTypeModel(util.transform(re.getRoomTypeEntity(), RoomTypeModel.class));
			roomModels.add(roomModel);
		});
		return Flux.just(roomModels);
	}

	@Override
	public Flux<List<RoomTypeModel>> fetchRoomTypes(String hotelId) {
		
		List<RoomTypeEntity> roomEntities = roomTypeDao.findByStatusAndHotelEntityHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		List<RoomTypeModel> roomTypeModels = new ArrayList<>();
		roomEntities.forEach(rte -> {
			RoomTypeModel roomTypeModel = new RoomTypeModel();
			roomTypeModel = util.transform(rte, RoomTypeModel.class);
			roomTypeModels.add(roomTypeModel);
		});
		return Flux.just(roomTypeModels);
	}

	@Override
	public Flux<List<FloorModel>> fetchFloors(String hotelId) {
		
		List<FloorEntity> floorEntities = floorDao.findByStatusAndHotelEntityHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		List<FloorModel> floorModels = new ArrayList<>();
		floorEntities.forEach(fl -> {
			FloorModel floorModel = new FloorModel();
			floorModel = util.transform(fl, FloorModel.class);
			List<RoomModel> roomModels = new ArrayList<>();
			fl.getRoomEntities().forEach(room -> {
				RoomModel roomModel = new RoomModel();
				roomModel = util.transform(room, RoomModel.class);
				roomModel.setHotelModel(util.transform(room.getHotelEntity(), HotelModel.class));
				roomModel.setRoomTypeModel(util.transform(room.getRoomTypeEntity(), RoomTypeModel.class));
				roomModels.add(roomModel);
			});
			floorModel.setRoomModels(roomModels);
			floorModels.add(floorModel);
		});
		return Flux.just(floorModels);
	}
}