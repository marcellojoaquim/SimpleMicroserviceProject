package com.mjsilva.vendas.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.mjsilva.vendas.domain.ProdutoQuantidade;
import com.mjsilva.vendas.domain.Venda;
import com.mjsilva.vendas.domain.Venda.Status;
import com.mjsilva.vendas.domain.dto.VendaDto;
import com.mjsilva.vendas.repository.IVendaRepository;
import com.mjsilva.vendas.service.ClienteService;

@ExtendWith(MockitoExtension.class)
public class CadastrarVendaTest {
	
	@Mock
	IVendaRepository vendaRepository;
	
	@InjectMocks
	CadastrarVenda cadastrarVenda;
	
	@Mock
	ModelMapper modelMapper;
	
	@Mock
	ClienteService clienteService;
	
	@Mock
	ProdutoQuantidade produtoQuantidade;
	
	private final String CODIGO = "venda01";
	private final String CODIGO_CLIENTE = "cli01";
	private final Status STATUS = Status.INICIADA;
	private Instant dataVenda = Instant.now();
	private Venda venda01;
	private Venda venda;
	private VendaDto dto;
	
	@BeforeEach
	void setUp() {
		venda01 = Venda.builder()
				.id("1234567899")
				.clienteId(CODIGO_CLIENTE)
				.codigo(CODIGO)
				.dataVenda(dataVenda)
				.produtos(Set.of(produtoQuantidade))
				.status(STATUS)
				.valorTotal(BigDecimal.valueOf(1500))
				.build();
		
		venda = Venda.builder()
				.clienteId(CODIGO_CLIENTE)
				.codigo(CODIGO)
				.dataVenda(dataVenda)
				.produtos(Set.of(produtoQuantidade))
				.status(STATUS)
				.valorTotal(BigDecimal.valueOf(1500))
				.build();				

		dto = VendaDto.builder()
				.clienteId(CODIGO_CLIENTE)
				.codigo(CODIGO)
				.dataVenda(dataVenda)
				.build();
	}
	
	@Test
	void cadastrarVenda() {
		when(modelMapper.map(eq(dto), eq(Venda.class))).thenReturn(venda01);
		
		Venda result = cadastrarVenda.cadastrar(dto);
		
		assertEquals(venda01.getCodigo(), result.getCodigo());
	}
}
