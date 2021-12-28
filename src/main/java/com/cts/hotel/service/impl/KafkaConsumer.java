package com.cts.hotel.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import com.cts.hotel.dao.RoomDao;
import com.cts.hotel.entity.RoomEntity;
import com.cts.hotel.helper.Util;
import com.cts.hotel.model.RoomModel;

import reactor.core.publisher.Flux;

@Service
public class KafkaConsumer implements CommandLineRunner {

	@Autowired
	private Util util;

	@Autowired
	private RoomDao roomDao;

	@Value("${not.found}")
	private String notFound;

	@Autowired
	private ReactiveKafkaConsumerTemplate<String, RoomModel> addRoomKafkaConsumerTemplate;
	
	@Autowired
	private ReactiveKafkaConsumerTemplate<String, RoomModel> updateRoomKafkaConsumerTemplate;

	private Flux<RoomModel> addRoom() {
		System.out.println("In Add Room Method");
		return addRoomKafkaConsumerTemplate.receiveAutoAck()
				// .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
				.doOnNext(consumerRecord -> System.out.println("received key ==>> "+ consumerRecord.key() +" value ==>> "+ consumerRecord.value() + " from topic ==>> "+ consumerRecord.topic()+"  offset ==>> "+ consumerRecord.offset()))
				.map(ConsumerRecord::value)
				.doOnNext(roomModel -> {
					System.out.println("Successfully consumed ==>> "+ RoomModel.class.getSimpleName() +" with value ==>> "+ roomModel);
					RoomEntity roomEntity = util.transform(roomModel, RoomEntity.class);
					 System.err.println("roomEntity ==>> "+roomEntity);
					 roomDao.save(roomEntity).log();
				})
				.doOnError(throwable -> System.err.println("something bad happened while consuming ==>> "+ throwable.getMessage()));
	}
	
	@Override
    public void run(String... args) {
        // we have to trigger consumption
		addRoom().subscribe();
		updateRoom().subscribe();
    }
	
	private Flux<RoomModel> updateRoom() {
		
		System.out.println("In Update Room Method");
		return updateRoomKafkaConsumerTemplate.receiveAutoAck()
				// .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
				.doOnNext(consumerRecord -> System.out.println("received key ==>> "+ consumerRecord.key() +" value ==>> "+ consumerRecord.value() + " from topic ==>> "+ consumerRecord.topic()+"  offset ==>> "+ consumerRecord.offset()))
				.map(ConsumerRecord::value)
				.doOnNext(roomModel -> {
					System.out.println("Successfully consumed ==>> "+ RoomModel.class.getSimpleName() +" with value ==>> "+ roomModel);
					roomDao.findById(roomModel.getRoomId()).map(roomEntity -> {
						roomEntity.setStatus(roomModel.getStatus());
						roomEntity.setRoomNumber(roomModel.getRoomNumber());
						roomEntity.setRoomType(roomModel.getRoomType());
						roomEntity.setFloorName(roomModel.getFloorName());
						roomEntity.setHotelId(roomModel.getHotelId());
						System.err.println("roomEntity ==>> "+roomEntity);
						return roomDao.save(roomEntity).log().map(re -> util.transform(re, RoomModel.class));
					});	
				})
				.doOnError(throwable -> System.err.println("something bad happened while consuming ==>> "+ throwable.getMessage()));
	}

}