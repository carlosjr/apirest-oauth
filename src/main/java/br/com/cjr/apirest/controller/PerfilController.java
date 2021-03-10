package br.com.cjr.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cjr.apirest.model.request.PerfilRequest;
import br.com.cjr.apirest.model.response.DemoResponse;
import br.com.cjr.apirest.model.response.PerfilResponse;
import br.com.cjr.apirest.service.PerfilService;

@RestController
@RequestMapping("/perfil")
public class PerfilController extends BaseController{

	@Autowired
	private PerfilService perfilService;
	
	@GetMapping
	public Page<PerfilResponse> listar(@RequestParam(required = false) String nome, 
			@PageableDefault(sort="nome", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		
		PerfilRequest perfilRequest = new PerfilRequest();
		perfilRequest.setNome(nome);
		return perfilService.listarPaginado(perfilRequest, paginacao);
	}
	
	@GetMapping("/{id}")
	public PerfilResponse buscarPorId(@PathVariable("id") Long id) {
		return perfilService.buscarPorId(id);
	}
	
	
	@PostMapping
	public ResponseEntity<PerfilResponse> salvar(@RequestBody PerfilRequest perfilRequest) {
		PerfilResponse perfilResponse = perfilService.salvarAtualizar(perfilRequest);
		return ResponseEntity.ok().body(perfilResponse);
	}
	
	@DeleteMapping
	public ResponseEntity<DemoResponse> cadastrarDemos(@RequestParam Long id) {
		perfilService.remover(id);
		return ResponseEntity.ok().build();
	}
	
}
