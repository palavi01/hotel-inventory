package com.cts.hotel.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

import com.cts.hotel.model.RoomModel;

import reactor.kafka.sender.SenderOptions;

@Configuration
public class KakfaProducerConfiguration {

    @Bean
    public ReactiveKafkaProducerTemplate<String, RoomModel> addRoomKafkaProducerTemplate(KafkaProperties properties) {
    	
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<String, RoomModel>(SenderOptions.create(props));
    }
    
    @Bean
    public ReactiveKafkaProducerTemplate<String, RoomModel> updateRoomKafkaProducerTemplate(KafkaProperties properties) {
    	
        Map<String, Object> props = properties.buildProducerProperties();
        return new ReactiveKafkaProducerTemplate<String, RoomModel>(SenderOptions.create(props));
    }
}