package com.cts.hotel.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.cts.hotel.model.RoomModel;
import com.cts.hotel.service.impl.SampleProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

@Configuration
public class KafkaProducerStream {

	@Value("${spring.kafka.url}")
	private String kafkaURL;

	@Value("${spring.kafka.port}")
	private String kafkaPort;
	
	@Value("${add_room_topic}")
    private String addRoomTopic;
	
	@Value("${update_room_topic}")
    private String updateRoomTopic;

	private SimpleDateFormat dateFormat ;
	private KafkaSender<Integer, String> sender ;
	private static final Logger log = LoggerFactory.getLogger(SampleProducer.class.getName());
	
	public KafkaProducerStream(){
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "hotel-producer");
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,  StringSerializer.class);
		SenderOptions<Integer, String> senderOptions = SenderOptions.create(props);

        sender = KafkaSender.create(senderOptions);
        dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");
	}
	
	  public void sendMessages(String topic, RoomModel roomModel, int count, CountDownLatch latch) throws InterruptedException {
		  try {
				String roomModelString = new ObjectMapper().writeValueAsString(roomModel);
				System.err.println("roomModelString ==>> " + roomModelString);
				Flux<SenderRecord<Integer , String, String>> outboundFlux = Flux.range(1, count)
				          .map(i -> SenderRecord.create(new ProducerRecord<>(addRoomTopic, roomModelString), roomModel.getCreatedDate()));
				sender.send(outboundFlux).subscribe();
				System.err.println("value send to kafka");
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
	  }
}