package com.cts.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cts.hotel.dao.FloorDao;
import com.cts.hotel.dao.RoomDao;
import com.cts.hotel.dao.RoomTypeDao;
import com.cts.hotel.entity.FloorEntity;
import com.cts.hotel.entity.RoomEntity;
import com.cts.hotel.entity.RoomTypeEntity;
import com.cts.hotel.helper.Status;
import com.cts.hotel.helper.Util;
import com.cts.hotel.model.FloorModel;
import com.cts.hotel.model.HotelModel;
import com.cts.hotel.model.RoomModel;
import com.cts.hotel.model.RoomTypeModel;
import com.cts.hotel.service.HotelInventoryService;

@Service
public class HotelInventoryServiceImpl implements HotelInventoryService {

	@Autowired
	private Util util;
	
	@Autowired
	private FloorDao floorDao;
	
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private RoomTypeDao roomTypeDao;
	
	@Value("${add_room_topic}")
    private String addRoomTopic;
	
	@Value("${update_room_topic}")
    private String updateRoomTopic;
	
	@Autowired
	private KafkaTemplate<String, RoomModel> kafkaTemplate;
	
	@Override
	public RoomModel createRoom(RoomModel roomModel) {
		
		kafkaTemplate.send(addRoomTopic, roomModel);
		return roomModel;
	}

	@Override
	public RoomModel updateRoom(RoomModel roomModel) {
		
		kafkaTemplate.send(updateRoomTopic, roomModel);
		return roomModel;
	}

	@Override
	public List<RoomModel> fetchRooms(String hotelId) {
		
		List<RoomEntity> roomEntities = roomDao.findByStatusAndHotelEntityHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		final List<RoomModel> roomModels = new ArrayList<>();
		if (!CollectionUtils.isEmpty(roomEntities)) {
			roomEntities.forEach(re -> {
				RoomModel roomModel = new RoomModel();
				roomModel = util.transform(re, RoomModel.class);
				roomModel.setFloorModel(util.transform(re.getFloorEntity(), FloorModel.class));
				roomModel.setHotelModel(util.transform(re.getHotelEntity(), HotelModel.class));
				roomModel.setRoomTypeModel(util.transform(re.getRoomTypeEntity(), RoomTypeModel.class));
				roomModels.add(roomModel);
			});
		}
		return roomModels;
	}

	@Override
	public List<RoomTypeModel> fetchRoomTypes(String hotelId) {
		
		List<RoomTypeEntity> roomTypeEntities = roomTypeDao.findByStatusAndHotelEntityHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		List<RoomTypeModel> roomTypeModels = new ArrayList<>();
		for(RoomTypeEntity rte : roomTypeEntities) {
			RoomTypeModel roomTypeModel = new RoomTypeModel();
			roomTypeModel = util.transform(rte, RoomTypeModel.class);
			roomTypeModels.add(roomTypeModel);
		}
		return roomTypeModels;
	}

	@Override
	public List<FloorModel> fetchFloors(String hotelId) {
		
		List<FloorEntity> floorEntities = floorDao.findByStatusAndHotelEntityHotelId(Status.ACTIVE.ordinal(), Long.valueOf(hotelId));
		List<FloorModel> floorModels = new ArrayList<>();
		for(FloorEntity fl : floorEntities) {
			FloorModel floorModel = new FloorModel();
			floorModel = util.transform(fl, FloorModel.class);
			List<RoomModel> roomModels = new ArrayList<>();
			for(RoomEntity room : fl.getRoomEntities()) {
				RoomModel roomModel = new RoomModel();
				roomModel = util.transform(room, RoomModel.class);
				roomModel.setHotelModel(util.transform(room.getHotelEntity(), HotelModel.class));
				roomModel.setRoomTypeModel(util.transform(room.getRoomTypeEntity(), RoomTypeModel.class));
				roomModels.add(roomModel);
			}
			floorModel.setRoomModels(roomModels);
			floorModels.add(floorModel);
		}
		return floorModels;
	}
}