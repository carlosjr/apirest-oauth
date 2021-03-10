package br.com.cjr.apirest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.cjr.apirest.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByEmail(String email);
	Optional<Usuario> findByEmailAndAtivo(String email, Boolean ativo);
	Usuario findById(Integer id);

}
