package com.cts.hotel.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Configuration
public class KafkaConsumerStream {

	@Value("${spring.kafka.url}")
	private String kafkaURL;

	@Value("${spring.kafka.port}")
	private String kafkaPort;

	@Value("${add_room_topic}")
	private String addRoomTopic;

	@Value("${update_room_topic}")
	private String updateRoomTopic;

	@Bean
	public KafkaReceiver<String, String> consumerFactory() throws InterruptedException {

		Map<String, Object> consumerProps = new HashMap<>();
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaURL + ":" + kafkaPort);
		consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "hotel-producer");
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
		consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
		consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		ReceiverOptions<String, String> receiverOptions = ReceiverOptions.create(consumerProps);
		receiverOptions.subscription(Collections.singleton(addRoomTopic));
		KafkaReceiver<String, String> receiver = KafkaReceiver.create(receiverOptions);

		return receiver;
	}

}
