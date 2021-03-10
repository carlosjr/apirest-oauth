package br.com.cjr.apirest.controller;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import br.com.cjr.apirest.controller.resposta.ErroResposta;
import br.com.cjr.apirest.exception.BadRequestException;
import br.com.cjr.apirest.exception.ServiceException;
import br.com.cjr.apirest.model.response.ErroValidacaoDto;

@CrossOrigin(origins = "*")
@RestController
public class BaseController {

	private static final Logger LOGGER = LogManager.getLogger(BaseController.class);

	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroValidacaoDto> handle(MethodArgumentNotValidException methodArgumentNotValidException) {
		List<ErroValidacaoDto> erros =  new ArrayList<>();
		
		List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroValidacaoDto erro = new ErroValidacaoDto(e.getField(), mensagem);
			erros.add(erro);
		});
		
		return erros;
		
	}

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErroResposta> tratarExcecao(ServiceException ex) {
		ErroResposta error = new ErroResposta();
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage(ex.getMessage());
		error.setHorario(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErroResposta> tratarExcecao(BadRequestException ex) {
		ErroResposta error = new ErroResposta();
		error.setStatus(HttpStatus.BAD_REQUEST);
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		error.setHorario(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErroResposta> tratarExcecao(AccessDeniedException ex) {
		ErroResposta error = new ErroResposta();
		error.setStatus(HttpStatus.FORBIDDEN);
		error.setErrorCode(HttpStatus.FORBIDDEN.value());
		error.setMessage(ex.getMessage());
		error.setHorario(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler
	public ResponseEntity<ErroResposta> tratarExcecao(Exception ex) {
		LOGGER.error("Não foi possível realizar a operação. Motivo: " + ex.getMessage(), ex);
		ErroResposta error = new ErroResposta();
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage("Não foi possível realizar a operação. Contate o administrador do sistema.");
		error.setHorario(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ErroValidacaoDto handle(EmptyResultDataAccessException emptyResultDataAccessException) {
		ErroValidacaoDto erro = new ErroValidacaoDto(null, "Resgistro não encontrado no banco de dados.");
		return erro;
	}

}
