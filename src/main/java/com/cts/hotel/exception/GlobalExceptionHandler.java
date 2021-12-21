package com.cts.hotel.exception;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
	
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

		DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
		var errorMessage = bufferFactory.wrap(ex.getMessage().split(",")[0].getBytes());
		if (ex instanceof HotelNotFoundException) {
			exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
			return exchange.getResponse().writeWith(Mono.just(errorMessage));
		} else if (ex instanceof DataNotFoundException) {
			exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
			return exchange.getResponse().writeWith(Mono.just(errorMessage));
		}

		exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		return exchange.getResponse().writeWith(Mono.just(errorMessage));
	}
}