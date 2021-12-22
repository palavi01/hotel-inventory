package com.cts.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.cts.hotel.dao.RoomDao;
import com.cts.hotel.entity.RoomEntity;
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
		roomDao.save(roomEntity);
	}

	@KafkaListener(topics = "#{'${update_room_topic}'}", groupId = "group-id", containerFactory = "updateRoomKafkaListenerFactory")
	public void updateRoom(RoomModel roomModel) {

		System.out.println("In Update Room Method");
		roomDao.findById(roomModel.getRoomId()).flatMap(roomEntity -> {
			roomEntity.setModifiedBy("1");
			roomEntity.setModifiedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
			roomEntity.setStatus(roomModel.getStatus());
			roomEntity.setRoomNumber(roomModel.getRoomNumber());
			roomEntity.setRoomType(roomModel.getRoomType());
			roomEntity.setFloorName(roomModel.getFloorName());
			roomEntity.setHotelId(roomModel.getHotelId());
			return roomDao.save(roomEntity);
		});
	}
}