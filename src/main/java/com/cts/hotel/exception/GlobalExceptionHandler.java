package com.cts.hotel.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.cts.hotel.helper.Util;
import com.cts.hotel.model.ErrorModel;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@Value("${404.error.message}")
	private String errorMessage;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(WebExchangeBindException.class)
    public List<ErrorModel> handleValidationExceptions(WebExchangeBindException ex) {

        List<ErrorModel> errorModels = new ArrayList<ErrorModel>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            ErrorModel errorModel = new ErrorModel();
            errorModel.setErrorMessage(error.getDefaultMessage());
            errorModel.setFieldName(((FieldError) error).getField());
            errorModels.add(errorModel);
        });
        return errorModels;
    }
	
	@org.springframework.web.bind.annotation.ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Object dataNotFoundExceptions(DataNotFoundException ex) {

		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
        body.put("message", ex.getLocalizedMessage());

        return body;
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ResponseStatusException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Object responseStatusException(ResponseStatusException ex) {

		Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
        body.put("message", ex.getLocalizedMessage());

        return body;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Object handleAnyException(Exception ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Util.getCurrentDateTime("dd-MM-yyyy HH:mm:ss"));
        body.put("message", ex.getLocalizedMessage());

        return body;
    }
}