package br.com.cjr.apirest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cjr.apirest.model.Demo;

public interface DemoRepository extends JpaRepository<Demo, Long>{
	
	List<Demo> findByNome(String nome);

	@Query("SELECT d FROM Demo d WHERE d.nome = :nome")
	Page<Demo> buscarPorNome(@Param("nome") String nome, Pageable paginacao);

}
