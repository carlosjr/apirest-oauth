package br.com.cjr.apirest.model.response;

import java.text.SimpleDateFormat;

import br.com.cjr.apirest.model.Usuario;

public class UsuarioResponse {
	
	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private Boolean ativo;
	private Boolean primeiroAcesso;
	private Boolean aceiteTermosUso;
	private String dataUltimoLogin;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
	public UsuarioResponse() {
		
	}
	
	public UsuarioResponse(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.ativo = usuario.getAtivo();
		this.primeiroAcesso = usuario.getPrimeiroAcesso();
		this.aceiteTermosUso = usuario.getAceiteTermosUso();
		if(usuario.getDataUltimoLogin() != null) {
			this.dataUltimoLogin = dateFormat.format(usuario.getDataUltimoLogin());
		}
	}
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Boolean getPrimeiroAcesso() {
		return primeiroAcesso;
	}
	public void setPrimeiroAcesso(Boolean primeiroAcesso) {
		this.primeiroAcesso = primeiroAcesso;
	}
	public Boolean getAceiteTermosUso() {
		return aceiteTermosUso;
	}
	public void setAceiteTermosUso(Boolean aceiteTermosUso) {
		this.aceiteTermosUso = aceiteTermosUso;
	}
	public String getDataUltimoLogin() {
		return dataUltimoLogin;
	}
	public void setDataUltimoLogin(String dataUltimoLogin) {
		this.dataUltimoLogin = dataUltimoLogin;
	}

}
