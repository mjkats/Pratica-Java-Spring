package br.com.mobicare.selecao.project.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.mobicare.selecao.project.dto.ColaboradorDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE colaborador SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Colaborador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	
	@Past(message = "Data de nascimento é uma data no passado")
	private LocalDate dataNascimento;
	
	@Nullable
	private int idade;
	
	@ManyToOne
	@JoinColumn(name = "setor_id")
	@JsonBackReference
	private Setor setor;
	
	private OffsetDateTime createdAt;
	
	private boolean deleted;
	
	public Colaborador(String cpf, String nome, String telefone, String email, String dataNascimento) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.dataNascimento = LocalDate.parse(dataNascimento);
		this.idade = Period.between(this.dataNascimento, LocalDate.now()).getYears();
	}
	
	public static Colaborador toColaborador(ColaboradorDTO colaboradorDto) {
		return new Colaborador(
				colaboradorDto.getCpf(),
				colaboradorDto.getNome(),
				colaboradorDto.getTelefone(),
				colaboradorDto.getEmail(),
				colaboradorDto.getDataNascimento()
		);
	}
	
	@PrePersist
	public void onCreate() {
		this.createdAt = OffsetDateTime.now();
		this.deleted = false;
	}
}
