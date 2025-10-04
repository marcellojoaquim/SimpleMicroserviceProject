package com.mjdsilva.cliente.service.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ClienteDto {
	
	@NotNull
	private Long cpf;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String segundoNome;
	
}
