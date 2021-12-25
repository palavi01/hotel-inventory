//package com.cts.hotel.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import com.cts.hotel.model.RoomModel;
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@EnableKafka
//@Configuration
//public class KafkaConsumerConfiguration {
//
//    @Value("${spring.kafka.url}")
//    private String kafkaURL;
//
//    @Value("${spring.kafka.port}")
//    private String kafkaPort;
//
//    @Bean
//    public ConsumerFactory<String, RoomModel> addRoomConsumerFactory() {
//
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaURL + ":" + kafkaPort);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "6000");
//        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//
//        ErrorHandlingDeserializer<String> headerErrorHandlingDeserializer = new ErrorHandlingDeserializer<>(new StringDeserializer());
//        ErrorHandlingDeserializer<RoomModel> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(new JsonDeserializer<>(RoomModel.class, objectMapper()));
//        return new DefaultKafkaConsumerFactory<>(config, headerErrorHandlingDeserializer, errorHandlingDeserializer);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, RoomModel> addRoomKafkaListenerFactory() {
//    	
//        ConcurrentKafkaListenerContainerFactory<String, RoomModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(addRoomConsumerFactory());
//        factory.setErrorHandler(new KafkaErrorHandler());
//        return factory;
//    }
//    
//    @Bean
//    public ConsumerFactory<String, RoomModel> updateRoomConsumerFactory() {
//
//        Map<String, Object> config = new HashMap<>();
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaURL + ":" + kafkaPort);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group-id");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        config.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "6000");
//        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//
//        ErrorHandlingDeserializer<String> headerErrorHandlingDeserializer = new ErrorHandlingDeserializer<>(new StringDeserializer());
//        ErrorHandlingDeserializer<RoomModel> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(new JsonDeserializer<>(RoomModel.class, objectMapper()));
//        return new DefaultKafkaConsumerFactory<>(config, headerErrorHandlingDeserializer, errorHandlingDeserializer);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, RoomModel> updateRoomKafkaListenerFactory() {
//    	
//        ConcurrentKafkaListenerContainerFactory<String, RoomModel> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(updateRoomConsumerFactory());
//        factory.setErrorHandler(new KafkaErrorHandler());
//        return factory;
//    }
//
//    private ObjectMapper objectMapper() {
//        return Jackson2ObjectMapperBuilder.json().visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY).featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).build();
//    }
//}