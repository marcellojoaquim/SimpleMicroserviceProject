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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContatosDto {
	
	@NotBlank
	private String email;
	
	@NotNull
	private Integer tel;
	
	@NotNull
	private Long clienteId;
}
