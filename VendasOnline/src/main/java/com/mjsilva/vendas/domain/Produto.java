package com.mjsilva.vendas.domain;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Produto {
	
	private String id;
	
	@NotNull
	@Size(min = 4, max = 10)
	private String codigo;
	
	@NotNull
	@Size(min = 5, max = 35)
	private String nome;
	
	
	@NotNull
	@Size(min = 20, max = 150)
	private String descricao;
	
	private BigDecimal valor;
	
	
}
