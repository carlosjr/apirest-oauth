package br.com.cjr.apirest.model.request;

import java.sql.Timestamp;

public class UsuarioRequest extends BaseRequest {
	
	private static final long serialVersionUID = 3021560496194583449L;

	private Long id;
	private String nome;
	private String email;
	private String senha;
	private Boolean ativo;
	private Boolean primeiroAcesso;
	private Boolean aceiteTermosUso;
	private Timestamp dataUltimoLogin;	

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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Timestamp getDataUltimoLogin() {
		return dataUltimoLogin;
	}
	public void setDataUltimoLogin(Timestamp dataUltimoLogin) {
		this.dataUltimoLogin = dataUltimoLogin;
	}
	public Boolean getAceiteTermosUso() {
		return aceiteTermosUso;
	}
	public void setAceiteTermosUso(Boolean aceiteTermosUso) {
		this.aceiteTermosUso = aceiteTermosUso;
	}
	public Boolean getPrimeiroAcesso() {
		return primeiroAcesso;
	}
	public void setPrimeiroAcesso(Boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}
	
	
	
}
