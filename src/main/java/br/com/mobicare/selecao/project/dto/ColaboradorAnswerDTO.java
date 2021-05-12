package br.com.mobicare.selecao.project.dto;

import br.com.mobicare.selecao.project.model.Colaborador;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ColaboradorAnswerDTO {

	private String nome;
	private String email;
	private int idade;
	
	public static ColaboradorAnswerDTO colaboradorToDto(Colaborador colaborador) {
		return new ColaboradorAnswerDTO(colaborador.getNome(), colaborador.getEmail(), colaborador.getIdade());
	}
}
