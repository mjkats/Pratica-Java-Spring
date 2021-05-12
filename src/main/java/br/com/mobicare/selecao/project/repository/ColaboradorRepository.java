package br.com.mobicare.selecao.project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.mobicare.selecao.project.model.Colaborador;
import br.com.mobicare.selecao.project.model.Setor;

public interface ColaboradorRepository extends CrudRepository<Colaborador, Long>{
	List<Colaborador> findByNomeLike(String nome);
	List<Colaborador> findByNomeAndSetor(String nome, Setor setor);
	List<Colaborador> findBySetor(Setor setor);
	List<Colaborador> findAll();
}
