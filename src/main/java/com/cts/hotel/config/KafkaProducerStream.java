package com.cts.hotel.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.cts.hotel.model.RoomModel;

import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

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

	@Bean
	public KafkaSender<String, RoomModel> producerFactory() throws InterruptedException {

		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaURL + ":" + kafkaPort);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "hotel-producer");
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		SenderOptions<String, RoomModel> senderOptions = SenderOptions.create(props);
		KafkaSender<String, RoomModel> sender = KafkaSender.create(senderOptions);
		
		return sender;
	}
}