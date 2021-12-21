package com.cts.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
	public RoomModel createRoom(RoomModel roomModel) {
		
		roomModel.setCreatedBy(1L);
		roomModel.setCreatedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
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
        
        roomEntity = roomDao.save(roomEntity);
        roomModel = util.transform(roomEntity, RoomModel.class);
        
		return roomModel;
	}

	@Override
	public RoomModel updateRoom(RoomModel roomModel) {
		
		Optional<RoomEntity> roomEntityOptional = roomDao.findById(Long.valueOf(roomModel.getRoomId()));
		if (roomEntityOptional.isPresent()) {
			RoomEntity roomEntity = roomEntityOptional.get();
			roomEntity.setModifiedBy(1L);
			roomEntity.setModifiedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
			roomEntity.setStatus(roomModel.getStatus());
			roomEntity.setRoomNumber(roomModel.getRoomNumber());
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
        
		return roomModel;
	}

	@Override
	public List<RoomModel> fetchRooms(String hotelId) {
		
		List<RoomEntity> roomEntities = roomDao.findByStatusAndHotelEntityHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		final List<RoomModel> roomModels = new ArrayList<>();
		if (!CollectionUtils.isEmpty(roomEntities)) {
			roomEntities.forEach(re -> {
				RoomModel roomModel = new RoomModel();
				roomModel = util.transform(re, RoomModel.class);
				roomModel.setFloorModel(util.transform(re.getFloorEntity(), FloorModel.class));
				roomModel.setHotelModel(util.transform(re.getHotelEntity(), HotelModel.class));
				roomModel.setRoomTypeModel(util.transform(re.getRoomTypeEntity(), RoomTypeModel.class));
				roomModels.add(roomModel);
			});
		}
		return roomModels;
	}

	@Override
	public List<RoomTypeModel> fetchRoomTypes(String hotelId) {
		
		List<RoomTypeEntity> roomTypeEntities = roomTypeDao.findByStatusAndHotelEntityHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		List<RoomTypeModel> roomTypeModels = new ArrayList<>();
		for(RoomTypeEntity rte : roomTypeEntities) {
			RoomTypeModel roomTypeModel = new RoomTypeModel();
			roomTypeModel = util.transform(rte, RoomTypeModel.class);
			roomTypeModels.add(roomTypeModel);
		}
		return roomTypeModels;
	}

	@Override
	public List<FloorModel> fetchFloors(String hotelId) {
		
		List<FloorEntity> floorEntities = floorDao.findByStatusAndHotelEntityHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		List<FloorModel> floorModels = new ArrayList<>();
		for(FloorEntity fl : floorEntities) {
			FloorModel floorModel = new FloorModel();
			floorModel = util.transform(fl, FloorModel.class);
			List<RoomModel> roomModels = new ArrayList<>();
			for(RoomEntity room : fl.getRoomEntities()) {
				RoomModel roomModel = new RoomModel();
				roomModel = util.transform(room, RoomModel.class);
				roomModel.setHotelModel(util.transform(room.getHotelEntity(), HotelModel.class));
				roomModel.setRoomTypeModel(util.transform(room.getRoomTypeEntity(), RoomTypeModel.class));
				roomModels.add(roomModel);
			}
			floorModel.setRoomModels(roomModels);
			floorModels.add(floorModel);
		}
		return floorModels;
	}
}