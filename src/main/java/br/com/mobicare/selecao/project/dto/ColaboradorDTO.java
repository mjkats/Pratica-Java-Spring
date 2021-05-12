package br.com.mobicare.selecao.project.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ColaboradorDTO {
	
	@NotNull(message = "Cpf é obrigatório")
	@Size(min = 11, max = 11, message = "Cpf deve ter 11 caracteres")
	private String cpf;
	
	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	
	@NotNull(message = "Telefone é obrigatório")
	@Size(min = 10, max = 13, message = "Telefone precisa ter entre 11 e 13 caracteres")
	private String telefone;
	
	@NotBlank(message = "Email é obrigatório")
	private String email;
	
	@NotBlank(message = "Data de nascimento é obrigatório")
	@Size(min = 10, max = 10, message = "Data de nascimento deve ter 10 caracteres, seguindo o formato aaaa-mm-dd")
	private String dataNascimento;
}
