package br.com.mobicare.selecao.project.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.mobicare.selecao.project.model.Setor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SetorAnswerDTO {
	
	private Long id;
	private String descricao;
	private List<ColaboradorAnswerDTO> colaboradores;
	
	public static SetorAnswerDTO setorToDto(Setor setor) {
		return new SetorAnswerDTO(
				setor.getId(), 
				setor.getDescricao(), 
				setor.getColaboradores().stream().map(c -> ColaboradorAnswerDTO.colaboradorToDto(c)).collect(Collectors.toList())
		);
	}
}
