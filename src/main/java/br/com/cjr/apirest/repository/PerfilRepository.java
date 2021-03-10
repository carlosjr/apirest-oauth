package br.com.cjr.apirest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cjr.apirest.model.Perfil;

//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference


@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{
	
	List<Perfil> findByNome(String nome);

	@Query("SELECT p FROM Perfil p WHERE p.nome = :nome")
	Page<Perfil> buscarPorNome(@Param("nome") String nome, Pageable paginacao);
	
	@Query(value = "SELECT p.*, u.nome as nomeUsuarioInclusao FROM perfil p "
			+ " LEFT JOIN usuario u ON u.id = p.id_usuario_inclusao"
			+ " WHERE p.id=?1", nativeQuery = true)
//	@Query(value = PostgresRepository.cons, nativeQuery = true)
	Perfil buscarPerfilPorId(Long id);
}
