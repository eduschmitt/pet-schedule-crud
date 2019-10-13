package com.sippulse.pet.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import lombok.Data;

/**
 * Classe padrão retornada quando ocorrem erros.
 * @author schmitt
 *
 */
@Data
class ApiError {

	private HttpStatus status;
	private String timestamp;
	private String message;
	private String debugMessage;

	private ApiError() {
		timestamp = LocalDateTime.now().toString();
	}

	ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
}
