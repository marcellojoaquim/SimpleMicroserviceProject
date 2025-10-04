package com.mjdsilva.cliente.service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ClienteDto {
	
	@NotNull
	private Long cpf;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String segundoNome;
	
}
