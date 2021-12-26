package com.cts.hotel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cts.hotel.dao.RoomDao;
import com.cts.hotel.helper.Util;
import com.cts.hotel.model.RoomModel;

import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverRecord;

@Service
public class KafkaConsumer {

	@Autowired
	private Util util;

	@Autowired
	private RoomDao roomDao;
	
	@Value("${not.found}")
	private String notFound;
	
	@Autowired
	private KafkaReceiver<String, String> receiver;
	
	public void addRoom(RoomModel roomModel) {

		System.out.println("In Add Room Method");
		Flux<ReceiverRecord<String, String>> inboundFlux = receiver.receive();
        inboundFlux.subscribe(r -> {
        	System.out.println("Received message: {}"+ r.value());
        	//RoomEntity roomEntity = util.transform(r.value(), RoomEntity.class);
    		//System.err.println("roomEntity ==>> "+roomEntity);
    		//roomDao.save(roomEntity).log();
            r.receiverOffset()
              .acknowledge();
        });
	}

//	@KafkaListener(topics = "#{'${update_room_topic}'}", groupId = "group-id", containerFactory = "updateRoomKafkaListenerFactory")
//	public void updateRoom(RoomModel roomModel) {
//
//		System.out.println("In Update Room Method");
//		roomDao.findById(roomModel.getRoomId()).flatMap(roomEntity -> {
//			roomEntity.setModifiedBy("1");
//			roomEntity.setModifiedDate(Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
//			roomEntity.setStatus(roomModel.getStatus());
//			roomEntity.setRoomNumber(roomModel.getRoomNumber());
//			roomEntity.setRoomType(roomModel.getRoomType());
//			roomEntity.setFloorName(roomModel.getFloorName());
//			roomEntity.setHotelId(roomModel.getHotelId());
//			System.err.println("roomEntity ==>> "+roomEntity);
//			return roomDao.save(roomEntity);
//		}).onErrorResume(e -> Mono.error(new DataNotFoundException("Room "+notFound)));
//	}
}