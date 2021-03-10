package br.com.cjr.apirest.model;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class OAuthUser extends User {

	private static final long serialVersionUID = 3602491453221338777L;
	private Integer id;
	private Long horario;
	private String perfil;
	private String nomeUsuario;
	private String email;
	private Boolean primeiroAcesso;
	private Boolean aceiteTermosUso;
	
	
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

	public OAuthUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public OAuthUser(String username, String password, String nomeUsuario, Boolean primeiroAcesso, Boolean aceiteTermosUso, Integer id, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
		this.email = username;
		this.horario = System.currentTimeMillis();
		this.nomeUsuario = nomeUsuario;
		this.primeiroAcesso = primeiroAcesso;
		this.aceiteTermosUso = aceiteTermosUso;
	}

	public OAuthUser(String username, String password, Boolean primeiroAcesso, Boolean aceiteTermosUso, Integer id, String perfil, String nomeUsuario,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
		this.perfil = perfil;
		this.horario = System.currentTimeMillis();
		this.nomeUsuario = nomeUsuario;
		this.primeiroAcesso = primeiroAcesso;
		this.aceiteTermosUso = aceiteTermosUso;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getHorario() {
		return horario;
	}

	public void setHorario(Long horario) {
		this.horario = horario;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object rhs) {
		return super.equals(rhs);
	}
	
}
