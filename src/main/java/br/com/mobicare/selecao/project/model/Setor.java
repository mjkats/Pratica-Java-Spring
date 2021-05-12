package br.com.mobicare.selecao.project.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Setor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Descrição é obrigatório")
	private String descricao;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "setor")
	@JsonManagedReference
	private List<Colaborador> colaboradores;
	
	public void addColaborador(Colaborador colaborador) {
		this.colaboradores.add(colaborador);
	}
}
