package com.cts.hotel.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class Util {

	@Autowired
	@Lazy
	private ModelMapper modelMapper;

	public <T> T transform(Object from, Class<T> valueType) {

		if (Objects.nonNull(from)) {
			return modelMapper.map(from, valueType);
		} else {
			return null;
		}
	}
	
	public String getCurrentDateTime(String pattern) {
		
		LocalDateTime now = LocalDateTime.now();  
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        String formatDateTime = now.format(format);  
        return formatDateTime;
	}
}