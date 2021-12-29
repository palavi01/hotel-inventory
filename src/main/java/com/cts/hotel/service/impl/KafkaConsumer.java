package com.cts.hotel.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import com.cts.hotel.dao.RoomDao;
import com.cts.hotel.entity.RoomEntity;
import com.cts.hotel.exception.DataNotFoundException;
import com.cts.hotel.helper.Util;
import com.cts.hotel.model.RoomModel;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class KafkaConsumer {

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

	public Flux<RoomModel> addRoom() {
		
		System.err.println("In Add Room Method");
		return addRoomKafkaConsumerTemplate.receiveAutoAck()
				// .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
				.doOnNext(consumerRecord -> System.out.println("received key ==>> "+ consumerRecord.key() +" value ==>> "+ consumerRecord.value() + " from topic ==>> "+ consumerRecord.topic()+"  offset ==>> "+ consumerRecord.offset()))
				.map(ConsumerRecord::value)
				.doOnNext(roomModel -> {
					System.out.println("Successfully consumed ==>> "+ RoomModel.class.getSimpleName() +" with value ==>> "+ roomModel);
					RoomEntity roomEntity = util.transform(roomModel, RoomEntity.class);
					 System.err.println("roomEntity ==>> "+roomEntity);
					 roomDao.save(roomEntity).log().subscribe();
				})
				.doOnError(throwable -> System.err.println("something bad happened while consuming ==>> "+ throwable.getMessage()));
	}
	
	public Flux<RoomModel> updateRoom() {
		
		System.err.println("In Update Room Method");
		return updateRoomKafkaConsumerTemplate.receiveAutoAck()
				// .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
				.doOnNext(consumerRecord -> System.out.println("received key ==>> "+ consumerRecord.key() +" value ==>> "+ consumerRecord.value() + " from topic ==>> "+ consumerRecord.topic()+"  offset ==>> "+ consumerRecord.offset()))
				.map(ConsumerRecord::value)
				.doOnNext(roomModel -> {
					System.out.println("Successfully consumed ==>> "+ RoomModel.class.getSimpleName() +" with value ==>> "+ roomModel);
					roomDao.findById(roomModel.getRoomId()).log().mapNotNull(re -> {
						roomModel.setCreatedDate(re.getCreatedDate());
						roomModel.setCreatedBy(re.getCreatedBy());
//						re.setStatus(roomModel.getStatus());
//						re.setRoomNumber(roomModel.getRoomNumber());
//						re.setRoomType(roomModel.getRoomType()); 
//						re.setFloorName(roomModel.getFloorName());
//						re.setHotelId(roomModel.getHotelId());
						System.err.println("re ==>> "+re);
						return re;
					}).subscribe();
				})
				.doOnNext(re -> roomDao.save(util.transform(re, RoomEntity.class)).log().subscribe())
				.doOnError(throwable -> System.err.println("something bad happened while consuming ==>> "+ throwable.getMessage()));
	}

}