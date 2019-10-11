package com.sippulse.pet.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sippulse.pet.exception.ServiceException.TipoExcecao;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle para a exceção ServiceException que a camada de serviço lança em caso de problemas.
     *
     * @param ex ServiceException
     * @return o ApiError
     */
    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<Object> handleService(
    		ServiceException ex) {
        ApiError apiError = new ApiError(ex.getTipoExcecao() == TipoExcecao.REGISTRO_NAO_ENCONTRADO ? NOT_FOUND : BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }
    
    /**
     * Handle para a exceção ServiceException que a camada de serviço lança em caso de problemas.
     *
     * @param ex ServiceException
     * @return o ApiError
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handle(
    		Exception ex) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage("Ocorreu uma exceção inesperada.");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

