package com.cts.hotel.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.cts.hotel.dao.RoomDao;
import com.cts.hotel.entity.RoomEntity;
import com.cts.hotel.exception.DataNotFoundException;
import com.cts.hotel.helper.Status;
import com.cts.hotel.helper.Util;
import com.cts.hotel.model.RoomModel;

@Service
public class KafkaConsumer {
    
	@Autowired
	private Util util;
	
	@Autowired
	private RoomDao roomDao;
	
    @KafkaListener(topics = "#{'${add_room_topic}'}", groupId = "group-id", containerFactory = "addRoomKafkaListenerFactory")
    public void addRoom(RoomModel roomModel) {
    	
    	System.out.println("In Add Room Method");
    	roomModel.setCreatedBy("1");
		roomModel.setCreatedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
        roomModel.setStatus(Status.ACTIVE.ordinal());
        RoomEntity roomEntity = util.transform(roomModel, RoomEntity.class);
        roomEntity = roomDao.save(roomEntity);
        roomModel = util.transform(roomEntity, RoomModel.class);
    }
    
    @KafkaListener(topics = "#{'${update_room_topic}'}", groupId = "group-id", containerFactory = "updateRoomKafkaListenerFactory")
    public void updateRoom(RoomModel roomModel) {
    	
    	System.out.println("In Update Room Method");
    	Optional<RoomEntity> roomEntityOptional = roomDao.findById(Long.valueOf(roomModel.getRoomId()));
		if (roomEntityOptional.isPresent()) {
			RoomEntity roomEntity = roomEntityOptional.get();
			roomEntity.setModifiedBy("1");
			roomEntity.setModifiedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
			roomEntity.setStatus(roomModel.getStatus());
			roomEntity.setRoomNumber(roomModel.getRoomNumber());
	        roomEntity = roomDao.save(roomEntity);
	        roomModel = util.transform(roomEntity, RoomModel.class);
        } else {
            throw new DataNotFoundException("Invalid Room Id");
        }
    }
}