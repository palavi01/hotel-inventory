package com.cts.hotel.config;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.cts.hotel.service.impl.SampleConsumer;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;


@Configuration
//@EnableKafka
public class KafkaConsumerStream {

	@Value("${spring.kafka.url}")
	private String kafkaURL;

	@Value("${spring.kafka.port}")
	private String kafkaPort;

	@Value("${update_room_topic}")
	private String updateRoomTopic;

	private static final Logger log = LoggerFactory.getLogger(SampleConsumer.class.getName());
	private final ReceiverOptions<String, String> receiverOptions;
	private final SimpleDateFormat dateFormat;

	public KafkaConsumerStream() {
		Map<String, Object> consumerProps = new HashMap<>();
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "hotel-producer");
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,IntegerDeserializer.class);
		consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
		consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		receiverOptions = ReceiverOptions.create(consumerProps);
		dateFormat = new SimpleDateFormat("HH:mm:ss:SSS z dd MMM yyyy");
	}

	public Disposable consumeMessages(String topic, CountDownLatch latch) throws InterruptedException {
		receiverOptions.subscription(Collections.singleton(topic))
				.addAssignListener(partitions -> log.debug("onPartitionsAssigned {}", partitions))
				.addRevokeListener(partitions -> log.debug("onPartitionsRevoked {}", partitions));
		Flux<ReceiverRecord<String, String>> kafkaFlux = KafkaReceiver.create(receiverOptions).receive();
		System.err.println("Coming");

		return kafkaFlux.subscribe(record -> {
			ReceiverOffset offset = record.receiverOffset();
			System.out.printf("Received message: topic-partition=%s offset=%d timestamp=%s key=%d value=%s\n",
					offset.topicPartition(),
					offset.offset(),
					dateFormat.format(new Date(record.timestamp())),
					record.key(), 
					record.value());
			offset.acknowledge();
			latch.countDown();
		});
	}

}
