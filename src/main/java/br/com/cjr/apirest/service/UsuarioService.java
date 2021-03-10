package br.com.cjr.apirest.service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.hash.Hashing;

import br.com.cjr.apirest.exception.BadRequestException;
import br.com.cjr.apirest.exception.ServiceException;
import br.com.cjr.apirest.model.Usuario;
import br.com.cjr.apirest.model.request.UsuarioRequest;
import br.com.cjr.apirest.model.response.UsuarioResponse;
import br.com.cjr.apirest.repository.UsuarioRepository;

import org.apache.commons.lang3.RandomStringUtils;

@Service
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	private static final Logger LOGGER = LogManager.getLogger(UsuarioService.class);
	
	private static final String TIPO_USUARIO_CLIENTE = "cliente";
	
	public void atualizarUltimoAcesso(UsuarioRequest usuarioRequest) {
		try {
			
			Usuario usuarioUpdate = usuarioRepository.findById(usuarioRequest.getIdUsuario());
			usuarioUpdate.setDataUltimoLogin(new Timestamp(System.currentTimeMillis()));
			
			if(Boolean.FALSE.equals(usuarioRequest.getPrimeiroAcesso())) {
				usuarioUpdate.setPrimeiroAcesso(true);
			}
			usuarioRepository.save(usuarioUpdate);
			
		} catch (Exception e) {
			LOGGER.error("Erro ao atualizar ultimo acesso.");
			throw new ServiceException(e);
		}
	}
	
	public void atualizarAceiteTermosUso(UsuarioRequest usuarioRequest) {
		try {			
			Usuario usuarioUpdate = usuarioRepository.findById(usuarioRequest.getIdUsuario());
			usuarioUpdate.setAceiteTermosUso(usuarioRequest.getAceiteTermosUso());
			usuarioRepository.save(usuarioUpdate);
		} catch (Exception e) {
			LOGGER.error("Erro ao atualizar aceite dos termos de uso.");
			throw new ServiceException(e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UsuarioResponse cadastrarAtualizar(UsuarioRequest usuarioRequest, String tipoUsuario) {
			
		if(usuarioRepository.findByEmail(usuarioRequest.getEmail()).isPresent()) {
			throw new BadRequestException("Já existe um usuário com o email informado.");
		}
		
		String pass = generatePasswordUsuario();
		
		Usuario usuario = new Usuario();
		usuario.setNome(usuarioRequest.getNome());
		usuario.setEmail(usuarioRequest.getEmail());
		usuario.setSenha(cryptPasswordUser(pass));
		usuario.setAceiteTermosUso(retornarPropriedadesUsuarioCliente(tipoUsuario));
		usuario.setPrimeiroAcesso(retornarPropriedadesUsuarioCliente(tipoUsuario));
		usuario.setAtivo(true);
		usuario.setDataInclusao(new Timestamp(System.currentTimeMillis()));
		usuario.setIdUsuarioInclusao(usuarioRequest.getIdUsuario());

		return new UsuarioResponse(usuarioRepository.save(usuario));
			
	}
	
	private Boolean retornarPropriedadesUsuarioCliente(String tipoUsuario) {
		return TIPO_USUARIO_CLIENTE.equals(tipoUsuario) ? true : null;
	}
	
	private String generatePasswordUsuario() {
		String password = RandomStringUtils.randomAlphanumeric(8) + RandomStringUtils.randomNumeric(1);
		return password;
	}
	
	private String cryptPasswordUser(String pass) {
		try {
			if (pass == null) {
				throw new ServiceException("Houve um erro ao gerar senha para o usuário.");
			}
			
			String md5Pass = Hashing.md5().hashString(pass, StandardCharsets.UTF_8).toString().toUpperCase();
			String sha512Pass = Hashing.sha512().hashString(md5Pass, StandardCharsets.UTF_8).toString().toUpperCase();
			
			return new BCryptPasswordEncoder().encode(sha512Pass);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
