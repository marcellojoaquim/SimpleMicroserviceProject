package com.mjsilva.vendas.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mjsilva.vendas.domain.Venda;
import com.mjsilva.vendas.domain.Venda.Status;
import com.mjsilva.vendas.repository.IVendaRepository;

@ExtendWith(MockitoExtension.class)
public class BuscarVendaTest {
	
	@InjectMocks
	BuscarVenda buscarVenda;
	
	@Mock
	IVendaRepository vendaRepository;
	
	private final String CODIGO = "venda01";
	private final String CODIGO_CLIENTE = "cli01";
	private final Status STATUS = Status.INICIADA;
	private Instant dataVenda = Instant.now();
	private Venda venda01;
	
	@BeforeEach
	void setUp() {
		venda01 = new Venda(
				"123456789", 
				CODIGO, 
				CODIGO_CLIENTE, 
				STATUS, 
				dataVenda, 
				null, 
				BigDecimal.valueOf(1000));
	}
	
	@Test
	@DisplayName("Buca uma venda pelo código")
	void buscarVendaPorCpdigo() {
		when(vendaRepository.findByCodigo(anyString())).thenReturn(Optional.of(venda01));
		
		Venda result = buscarVenda.buscarPorCodigo(CODIGO);
		
		Mockito.verify(vendaRepository, times(1)).findByCodigo(CODIGO);
		assertEquals(venda01.getCodigo(), result.getCodigo());
	}
	
	
}
