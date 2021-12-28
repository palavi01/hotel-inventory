package com.cts.hotel.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;

import com.cts.hotel.model.RoomModel;

import reactor.kafka.receiver.ReceiverOptions;

@Configuration
public class KafkaConsumerConfiguration {

	@Bean
    public ReceiverOptions<String, RoomModel> addRoomKafKaReceiverOptions(@Value(value = "${add_room_topic}") String topic, KafkaProperties kafkaProperties) {
        ReceiverOptions<String, RoomModel> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, RoomModel> addRoomKafkaConsumerTemplate(ReceiverOptions<String, RoomModel> addRoomKafKaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<String, RoomModel>(addRoomKafKaReceiverOptions);
    }
    
    @Bean
    public ReceiverOptions<String, RoomModel> updateRoomKafkaReceiverOptions(@Value(value = "${update_room_topic}") String topic, KafkaProperties kafkaProperties) {
        ReceiverOptions<String, RoomModel> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, RoomModel> updateRoomKafkaConsumerTemplate(ReceiverOptions<String, RoomModel> updateRoomKafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<String, RoomModel>(updateRoomKafkaReceiverOptions);
    }
}