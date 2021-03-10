package br.com.cjr.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cjr.apirest.model.request.UsuarioRequest;
import br.com.cjr.apirest.model.response.UsuarioResponse;
import br.com.cjr.apirest.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usuario")
@Api(value = "Usuario")
public class UsuarioController extends BaseController {
	
	@Autowired
	private UsuarioService usuarioservice;
	
	@ApiOperation(value = "Atualização do ultimo acesso do usuário.")
	@PutMapping("/atualizarUltimoAcesso")
	public ResponseEntity<HttpStatus> atualizarUltimoAcesso(@RequestBody UsuarioRequest usuarioRequest) {
		usuarioservice.atualizarUltimoAcesso(usuarioRequest);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@ApiOperation(value = "Cadastrar usuário de backoffice.")
	@PostMapping
	public ResponseEntity<UsuarioResponse> cadastrarAtualizarUsuario(@RequestBody UsuarioRequest usuarioRequest, @RequestParam(required=true) String tipo) {
		UsuarioResponse usuario = usuarioservice.cadastrarAtualizar(usuarioRequest, tipo);
		return ResponseEntity.ok().body(usuario);			
	}

}
