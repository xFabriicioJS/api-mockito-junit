package br.com.fabricio.api.resources.exceptions;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.fabricio.api.services.exceptions.DataIntegratyViolationException;
import br.com.fabricio.api.services.exceptions.ObjectNotFoundException;

//Manipulador de exceções, o que irá fazer o controle das exceções da nossa API.

@ControllerAdvice
public class ResourceExceptionHandler {

	// Excecao de objeto não encontrado
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
		StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	// Exceçãi de violação na integridade de dados do banco
	//Por ex: o cliente tenta cadastrar um usuário com um email já presente no banco de dados, então interceptamos essa exceção e jogamos um bad request.
	@ExceptionHandler(DataIntegratyViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityException(DataIntegratyViolationException ex, HttpServletRequest request) {
		StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
