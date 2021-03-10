package br.com.cjr.apirest.model.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.cjr.apirest.model.Demo;

public class DemoResponse {

	private String nome;
	private LocalDateTime dataCriacao;
	
	
	public DemoResponse(Demo demo) {
		this.nome = demo.getNome();
		this.dataCriacao = demo.getDataCriacao();
	}

	public String getNome() {
		return nome;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public static List<DemoResponse> converter(List<Demo> demos) {
		return demos.stream().map(DemoResponse::new).collect(Collectors.toList());
	}
	
	public static Page<DemoResponse> converter(Page<Demo> demos) {
		return demos.map(DemoResponse::new);
	}
}
