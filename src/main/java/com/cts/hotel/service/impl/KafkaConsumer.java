package com.cts.hotel.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.cts.hotel.model.RoomModel;

@Service
public class KafkaConsumer {
    
    @KafkaListener(topics = "#{'${room_topic}'}", groupId = "group-id", containerFactory = "roomKafkaListenerFactory")
    public void addUpdateRoom(RoomModel roomModel) {
    	
    	System.out.println("In Add/Update Room Method");
    }
}