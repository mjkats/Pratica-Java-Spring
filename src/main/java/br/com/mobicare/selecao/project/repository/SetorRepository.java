package br.com.mobicare.selecao.project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.mobicare.selecao.project.model.Setor;

public interface SetorRepository extends CrudRepository<Setor, Long> {
	List<Setor> findAll();
}
