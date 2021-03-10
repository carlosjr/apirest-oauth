package br.com.cjr.apirest.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DemoRequest {
	
	@NotNull @NotEmpty
	private String nome;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
