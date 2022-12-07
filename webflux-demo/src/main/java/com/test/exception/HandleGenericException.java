package com.test.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class HandleGenericException {

	@ExceptionHandler(value = MyException.class)
	public final ResponseEntity<?> handleCustomRuntimeException(MyException exception) {
//	    List<ApiSubError> subErrors = Arrays.asList(new ApiSubError(exception.getMessage()));
//	    ApiResponse response = new ApiErrorResponse(exception.getMessage(), exception.getErrorCode());
//	    LOG.error("result: {}", response.getResult());
		log.error("Exception caught globally ::::: ", exception.getMessage());
	    return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
	}
}
