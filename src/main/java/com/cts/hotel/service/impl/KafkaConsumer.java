package com.cts.hotel.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
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
import com.cts.hotel.model.RoomModel;

@Service
public class KafkaConsumer {
    
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
	
    @KafkaListener(topics = "#{'${add_room_topic}'}", groupId = "group-id", containerFactory = "addRoomKafkaListenerFactory")
    public void addRoom(RoomModel roomModel) {
    	
    	System.out.println("In Add Room Method");
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
    }
    
    @KafkaListener(topics = "#{'${update_room_topic}'}", groupId = "group-id", containerFactory = "updateRoomKafkaListenerFactory")
    public void updateRoom(RoomModel roomModel) {
    	
    	System.out.println("In Update Room Method");
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
	        
	        roomEntity = roomDao.save(roomEntity);
	        roomModel = util.transform(roomEntity, RoomModel.class);
        } else {
            throw new DataNotFoundException("Invalid Room Id");
        }
    }
}