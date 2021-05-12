package br.com.mobicare.selecao.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.mobicare.selecao.project.dto.ColaboradorDTO;
import br.com.mobicare.selecao.project.dto.SetorAnswerDTO;
import br.com.mobicare.selecao.project.model.Colaborador;
import br.com.mobicare.selecao.project.service.ColaboradorService;
import br.com.mobicare.selecao.project.service.SetorService;

@RestController
public class MainController {

	@Autowired
	private ColaboradorService colaboradorService;
	

	@Autowired
	private SetorService setorService;
	
	@PostMapping("colaborador/setor/{setorId}")
	public ResponseEntity<String> insertColaborador (@PathVariable(value = "setorId") Long setorId, @RequestBody @Valid ColaboradorDTO colaboradorDTO) {
		return setorService.insertColaborador(colaboradorDTO, setorId);
	}
	
	@GetMapping("setores")
	public ResponseEntity<List<SetorAnswerDTO>> getAllSetores() {
		return setorService.getAllSetores();
	}
	
	@GetMapping("colaboradores/nome/{nome}")
	public ResponseEntity<List<Colaborador>> findColaboradorByName (@PathVariable(value = "nome") String nome) {
		return colaboradorService.findColaboradorByName(nome);
	}
	
	@GetMapping("colaborador/{id}")
	public ResponseEntity<Colaborador> findColaboradorById (@PathVariable(value = "id") Long id) {
		return colaboradorService.findColaboradorById(id);
	}
	
	@DeleteMapping("colaborador/{id}")
	public ResponseEntity<Colaborador> deleteColaboradorById (@PathVariable(value = "id") Long id) {
		return colaboradorService.deleteColaboradorById(id);
	}
}
