package br.com.cjr.apirest.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.cjr.apirest.model.response.ErroValidacaoDto;

@RestControllerAdvice
public class ValidacaoHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
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
	
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ErroValidacaoDto handle(EmptyResultDataAccessException emptyResultDataAccessException) {
		ErroValidacaoDto erro = new ErroValidacaoDto(null, "Resgistro não encontrado no banco de dados");
		return erro;
	}
	
}
