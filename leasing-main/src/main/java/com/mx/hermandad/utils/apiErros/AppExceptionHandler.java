package com.mx.hermandad.utils.apiErros;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author Ing.Luis Arturo Aguilar Limas
 */
@RestControllerAdvice
public class AppExceptionHandler {

	@ResponseBody
	@ExceptionHandler(value = ExceptionManager.class)
	public ResponseEntity<?> handleException(ExceptionManager ex) {
		ApiError apiError = null;
		if (ex.getEx().getMessage().isBlank()) {
			apiError = new ApiError(ex.getStatus(), ex.getMessage());
		} else {
			apiError = new ApiError(ex.getStatus(), ex.getMessage(), ex.getEx());
		}
		return ResponseEntity.status(apiError.getStatus()).body(apiError);
	}
}
