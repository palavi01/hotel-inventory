package com.cts.hotel.service;

import java.util.List;

import com.cts.hotel.model.FloorModel;
import com.cts.hotel.model.RoomModel;
import com.cts.hotel.model.RoomTypeModel;

public interface HotelInventoryService {

	RoomModel createRoom(RoomModel roomModel);

	RoomModel updateRoom(RoomModel roomModel);

	List<RoomModel> fetchRooms(String hotelId);
	
	List<RoomTypeModel> fetchRoomTypes(String hotelId);

	List<FloorModel> fetchFloors(String hotelId);
}