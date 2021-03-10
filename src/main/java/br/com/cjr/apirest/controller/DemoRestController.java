package br.com.cjr.apirest.controller;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cjr.apirest.model.Demo;
import br.com.cjr.apirest.model.request.DemoRequest;
import br.com.cjr.apirest.model.response.DemoResponse;
import br.com.cjr.apirest.repository.DemoRepository;

@RestController
@RequestMapping("/demo")
public class DemoRestController {

	@Autowired
	private DemoRepository demoRepository;
	
	// Paginacao manual
	@GetMapping("/bancos")
	public Page<DemoResponse> listarDemos(@RequestParam(required = false) String nome, 
			@RequestParam int pagina, @RequestParam int qtd, @RequestParam(required = false) String ordenacao) {
		Pageable paginacao = PageRequest.of(pagina, qtd, Direction.DESC, ordenacao);
		
		Page<Demo> demos;
		if(nome == null) {
			demos = demoRepository.findAll(paginacao);
		} else {
			demos = demoRepository.buscarPorNome(nome, paginacao);
			
		}
		return DemoResponse.converter(demos);
	}
	
	// Com Pageable, habilitar no main o web suport, e passar parametros page, size, sort(id,asc)
	@GetMapping("/bancos-page")
	@Cacheable(value = "listaDeDemos")
	public Page<DemoResponse> listarDemos(@RequestParam(required = false) String nome, 
			@PageableDefault(sort="id", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		
		Page<Demo> demos;
		if(nome == null) {
			demos = demoRepository.findAll(paginacao);
		} else {
			demos = demoRepository.buscarPorNome(nome, paginacao);
			
		}
		return DemoResponse.converter(demos);
	}
	
	@PostMapping("/banco")
	@CacheEvict(value = "listaDeDemos", allEntries = true)
	public ResponseEntity<DemoResponse> cadastrarDemos(@RequestBody DemoRequest demoRequest, UriComponentsBuilder uriComponentsBuilder) {
		Demo demo = new Demo(demoRequest);
		demo.setDataCriacao(LocalDateTime.now());
		demoRepository.save(demo);
		
		URI uri = uriComponentsBuilder.path("/demo/{id}").buildAndExpand(demo.getId()).toUri();
		return ResponseEntity.created(uri).body(new DemoResponse(demo));
	}
	
}
