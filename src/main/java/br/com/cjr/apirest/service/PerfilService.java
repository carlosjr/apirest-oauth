package br.com.cjr.apirest.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.cjr.apirest.exception.ServiceException;
import br.com.cjr.apirest.model.Perfil;
import br.com.cjr.apirest.model.request.PerfilRequest;
import br.com.cjr.apirest.model.response.PerfilResponse;
import br.com.cjr.apirest.repository.PerfilRepository;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository perfilRepository;
	
	public List<PerfilResponse> listarTodos(PerfilRequest perfilRequest) {
		if(ObjectUtils.isEmpty(perfilRequest.getNome())) {
			return PerfilResponse.converter(perfilRepository.findByNome(perfilRequest.getNome()));
		}
		return PerfilResponse.converter(perfilRepository.findAll());
	}
	
	public Page<PerfilResponse> listarPaginado(PerfilRequest perfilRequest, Pageable pageable) {
		if(ObjectUtils.isEmpty(perfilRequest.getNome())) {
			return PerfilResponse.converter(perfilRepository.buscarPorNome(perfilRequest.getNome(), pageable));
		}
		return PerfilResponse.converter(perfilRepository.findAll(pageable));
	}
	
	public PerfilResponse buscarPorId(Long idPerfil) {
		Perfil perfil = perfilRepository.buscarPerfilPorId(idPerfil);
		if(perfil == null) {
			throw new ServiceException("Nenhum registro encontrado!");
		}
		return new PerfilResponse(perfil);
	}

	public PerfilResponse salvarAtualizar(PerfilRequest perfilRequest) {
		
		Perfil perfil = new Perfil();
		perfil.setId(perfilRequest.getId());
		perfil.setNome(perfilRequest.getNome());
		perfil.setAtivo(perfilRequest.getAtivo());
		perfil.setIdUsuarioInclusao(perfilRequest.getIdUsuario());
		
		if(perfilRequest.getId() != null) {
			atualizar(perfil);
		}
		
		return  new PerfilResponse(perfilRepository.save(perfil));
	}
	
	public PerfilResponse atualizar(Perfil perfil) {
		perfil.setDataAlteracao(new Timestamp(System.currentTimeMillis()));
		return new PerfilResponse(perfilRepository.save(perfil));
	}
	
	public void remover(Long id) {
		perfilRepository.deleteById(id);
	}
	
}
