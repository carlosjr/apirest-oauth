package br.com.cjr.apirest.controller.resposta;

import java.sql.Timestamp;
import org.springframework.http.HttpStatus;

public class ErroResposta {

	private Integer errorCode;
	private String message;
	private HttpStatus status;
	private Timestamp horario;

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getHorario() {
		return horario;
	}

	public void setHorario(Timestamp horario) {
		this.horario = horario;
	}

}
