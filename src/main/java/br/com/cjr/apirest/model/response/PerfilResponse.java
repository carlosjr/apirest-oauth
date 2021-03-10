package br.com.cjr.apirest.model.response;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.cjr.apirest.model.Perfil;

public class PerfilResponse {

	private Long id;
	private String nome;
	private Integer idUsuarioInclusao;
	private Integer idUsuarioAlteracao;
	private String nomeUsuarioInclusao;
	private String nomeUsuarioAlteracao;
	private String dataInclusao;
	private String dataAlteracao;
	private Boolean ativo;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public PerfilResponse() {
	}

	public PerfilResponse(Perfil perfil) {
		this.id = perfil.getId();
		this.nome = perfil.getNome();
		this.dataInclusao = dateFormat.format(perfil.getDataInclusao());
		this.dataAlteracao = dateFormat.format(perfil.getDataAlteracao());
//		this.nomeUsuarioInclusao = 
//		this.nomeUsuarioAlteracao = 
		this.ativo = perfil.getAtivo();
	}

	public Long getId() {
		return id;
	} 
	
	public String getNome() {
		return nome;
	}

	public String getDataInclusao() {
		return dataInclusao;
	}
	
	public Integer getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}

	public Integer getIdUsuarioAlteracao() {
		return idUsuarioAlteracao;
	}

	public String getDataAlteracao() {
		return dataAlteracao;
	}

	public String getNomeUsuarioInclusao() {
		return nomeUsuarioInclusao;
	}

	public String getNomeUsuarioAlteracao() {
		return nomeUsuarioAlteracao;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}

	public static List<PerfilResponse> converter(List<Perfil> demos) {
		return demos.stream().map(PerfilResponse::new).collect(Collectors.toList());
	}
	
	public static Page<PerfilResponse> converter(Page<Perfil> demos) {
		return demos.map(PerfilResponse::new);
	}
}
