package com.mjdsilva.produto.service.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "produto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Produto", description = "Entidade produto")
public class Produto {

	public enum Status{
		ATIVO,
		INATIVO
	}
	
	@Id
	private String id;
	
	@NotNull
	@Size(min = 4, max = 10)
	@Indexed(unique = true, background = true)
	private String nome;
	
	@NotNull
	@Size(min = 4, max = 10)
	@Indexed(unique = true, background = true)
	private String codigo;
	
	@NotNull
	@Size(min = 10, max = 150)
	private String descricao;
	
	
	private BigDecimal valor;
	
	private Status status;
}
