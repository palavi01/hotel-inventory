package com.cts.hotel.exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.cts.hotel.helper.Util;

@RestControllerAdvice
public class ExceptionHandler {

	@Value("${404.error.message}")
	private String errorMessage;

	@org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public HashMap<String, String> handleNoHandlerFound(NoHandlerFoundException e, WebRequest request) {

		HashMap<String, String> response = new HashMap<>();
		response.put("status", "fail");
		response.put("timestamp", Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
		response.put("message", errorMessage);
		return response;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public Object handleAnyException(Exception e, WebRequest request) {

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("timestamp", Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
		response.put("message", e.getLocalizedMessage());

		return response;
	}
}
