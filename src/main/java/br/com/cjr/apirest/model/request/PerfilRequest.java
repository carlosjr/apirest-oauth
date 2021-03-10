package br.com.cjr.apirest.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PerfilRequest extends BaseRequest{
	
	private static final long serialVersionUID = 8816281840512159569L;

	private Long id;
	private Boolean ativo;

	@NotNull @NotEmpty
	private String nome;
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
