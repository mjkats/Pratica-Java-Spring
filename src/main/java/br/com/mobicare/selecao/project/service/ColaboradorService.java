package br.com.mobicare.selecao.project.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.mobicare.selecao.project.model.Colaborador;
import br.com.mobicare.selecao.project.repository.ColaboradorRepository;

@Service
public class ColaboradorService {
	
	@Autowired
	ColaboradorRepository colaboradorRepository;
	
	public ResponseEntity<List<Colaborador>> findColaboradorByName(String nome) {
		return new ResponseEntity<>(colaboradorRepository.findByNomeLike(nome), HttpStatus.OK);
	}
	
	public ResponseEntity<Colaborador> findColaboradorById(Long id) {
		Optional<Colaborador> optColaborador = colaboradorRepository.findById(id);
		if (optColaborador.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(optColaborador.get(), HttpStatus.OK);
	}
	
	public ResponseEntity<Colaborador> deleteColaboradorById(Long id) {
		Optional<Colaborador> optColaborador = colaboradorRepository.findById(id);
		if (optColaborador.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		colaboradorRepository.deleteById(optColaborador.get().getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Scheduled(cron = "0 1 0 * * *")
	public void updateColaboradorAge() {
		List<Colaborador> colaboradores = colaboradorRepository.findAll();
		
		for (Colaborador c : colaboradores) {
			int novaIdade = Period.between(c.getDataNascimento(), LocalDate.now()).getYears();
			
			if (novaIdade != c.getIdade()) {
				c.setIdade(novaIdade);
				colaboradorRepository.save(c);
			}
		}
	}
}
