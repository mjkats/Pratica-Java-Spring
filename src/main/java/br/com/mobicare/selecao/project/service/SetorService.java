package br.com.mobicare.selecao.project.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import br.com.mobicare.selecao.project.dto.ColaboradorDTO;
import br.com.mobicare.selecao.project.dto.SetorAnswerDTO;
import br.com.mobicare.selecao.project.model.Colaborador;
import br.com.mobicare.selecao.project.model.Setor;
import br.com.mobicare.selecao.project.repository.ColaboradorRepository;
import br.com.mobicare.selecao.project.repository.SetorRepository;

@Service
public class SetorService {

	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	@CachePut("setores")
	public ResponseEntity<List<SetorAnswerDTO>> getAllSetores() {
		 List<Setor> setores = setorRepository.findAll();
		 List<SetorAnswerDTO> setoresDto = setores.stream().map( s -> SetorAnswerDTO.setorToDto(s)).collect(Collectors.toList());
		 return new ResponseEntity<> (setoresDto, HttpStatus.OK);
	}
	
	public ResponseEntity<String> insertColaborador(ColaboradorDTO colaboradorDto, Long setorId) {
		try {
			Colaborador colaborador = Colaborador.toColaborador(colaboradorDto);
			
			Optional<Setor> optSetor = setorRepository.findById(setorId);
			if (optSetor.isEmpty())
				return new ResponseEntity<> ("O setor " + setorId + " não existe", HttpStatus.NOT_FOUND);
			
			Setor setor = optSetor.get();
			colaborador.setIdade(Period.between(colaborador.getDataNascimento(), LocalDate.now()).getYears());
			
			if (!isNewColaboradorAllowed(setor, colaborador))
				return new ResponseEntity<> ("O colaborador não pode ser inserido" ,HttpStatus.BAD_REQUEST);
			
			colaborador.setSetor(setor);
			setor.addColaborador(colaborador);
			
			setorRepository.save(setor);
			return new ResponseEntity<> (HttpStatus.CREATED);
			
		} catch (DateTimeParseException dtpe) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de nascimento deve estar no formato aaaa-mm-dd", dtpe);
		}
	}
	
	public boolean isNewColaboradorAllowed(Setor setor, Colaborador newColaborador) {
		isNewColaboradorAlreadyInSystem(newColaborador);
		
		boolean isMinorAllowed = true;
		boolean isElderAllowed = true;
		
		if (newColaborador.getIdade() < 18)
			isMinorAllowed = canMinorBeAdded(setor);
		
		if (newColaborador.getIdade() > 65)
			isElderAllowed = canElderBeAdded();
		
		return isMinorAllowed && isElderAllowed && isNewColaboradorBlacklisted(newColaborador);	
	}
	
	public boolean canMinorBeAdded(Setor setor) {
		double limit = 0.2;
		int numberOfMinors = 1;
		List<Colaborador> colaboradores = colaboradorRepository.findBySetor(setor);
		
		for (Colaborador c : colaboradores) {
			if (c.getIdade() < 18)
				numberOfMinors++;
		}
		
		double minorsPercentage = (double) numberOfMinors / (colaboradores.size() + 1);
		if (minorsPercentage >= limit)
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, 
					"Não é possível cadastrar colaborador por estar acima do limite aceitável de menores de idade no sistema");
		
		return true;
	}
	
	public boolean canElderBeAdded() {
		double limit = 0.2;
		int numberOfElders = 1;
		List<Colaborador> colaboradores = colaboradorRepository.findAll();
		
		for (Colaborador c : colaboradores) {
			if (c.getIdade() > 65)
				numberOfElders++;
		}

		double eldersPercentage = (double) numberOfElders / (colaboradores.size() + 1);
		if (eldersPercentage >= limit)
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, 
					"Não é possível cadastrar colaborador por estar acima do limite aceitável de idosos no sistema");
		
		return true; 
	}
	
	public boolean isNewColaboradorBlacklisted(Colaborador newColaborador) {
		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<Colaborador[]> response = restTemplate
					.getForEntity("https://5e74cb4b9dff120016353b04.mockapi.io/api/v1/blacklist", Colaborador[].class);
		
			Colaborador[] blacklistedArray = response.getBody();
			List<Colaborador> blacklistedList = Arrays.asList(blacklistedArray);
			
			for (Colaborador blacklisted : blacklistedList) {
				if (newColaborador.getCpf().equals(blacklisted.getCpf()))
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "É proibido cadastrar este colaborador");
			}
			
			return true;
		} catch (HttpStatusCodeException hsce) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível cadastrar colaboradores no momento");
		}
	}
	
	public boolean isNewColaboradorAlreadyInSystem(Colaborador newColaborador) {
		List<Colaborador> colaboradores = colaboradorRepository.findAll();
		for (Colaborador c : colaboradores) {
			if (newColaborador.getCpf().equals(c.getCpf()))
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O colaborador já se encontra cadastrado no sistema");
		}
		
		return false;
	}
}
