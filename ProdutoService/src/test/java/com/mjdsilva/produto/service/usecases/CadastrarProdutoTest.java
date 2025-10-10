package com.mjdsilva.produto.service.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mjdsilva.produto.service.domain.Produto;
import com.mjdsilva.produto.service.domain.Produto.Status;
import com.mjdsilva.produto.service.repository.IProdutoRepository;

@ExtendWith(MockitoExtension.class)
public class CadastrarProdutoTest {
	
	@Mock
	IProdutoRepository produtoRepository;

	@InjectMocks
	CadastrarProduto cadastrarProduto;
	
	private Produto produto1;
	private Produto produtoAtualizado;
	private final Status statusAtivo = Status.ATIVO;
	private final Status statusInativo = Status.ATIVO;
	
	@BeforeEach
	void setUp() {
		produto1 = Produto.builder()
				.id("1234567899")
				.nome("Produto 1")
				.codigo("1234A")
				.descricao("Produto teste 01")
				.status(statusAtivo)
				.valor(BigDecimal.valueOf(150)).build();
		
	}
	
	@Test
	@DisplayName("Deve cadastrar um produto")
	void cadastrarProduto() {
		when(produtoRepository.insert(produto1)).thenReturn(produto1);
		
		Produto result = cadastrarProduto.cadastrar(produto1);
		
		Mockito.verify(produtoRepository, times(1)).insert(produto1);
		assertNotNull(result);
		assertEquals(produto1.getNome(), result.getNome());
	}
	
	@Test
	@DisplayName("Deve atualizar um produto")
	void atualizarProduto(){
		produtoAtualizado = Produto.builder()
				.nome("Produto 2")
				.codigo("1234B")
				.descricao("Produto teste 02")
				.status(statusInativo)
				.valor(BigDecimal.valueOf(130)).build();
		
		when(produtoRepository.save(produtoAtualizado)).thenReturn(produtoAtualizado);
		
		Produto result = cadastrarProduto.atualizar(produtoAtualizado);
		
		Mockito.verify(produtoRepository, times(1)).save(produtoAtualizado);
		assertNotNull(result);
		assertEquals(produtoAtualizado.getNome(), result.getNome());
		
	}
	
	@Test
	@DisplayName("Remover produto")
	void removerProduto() {
		when(produtoRepository.findById(produto1.getId())).thenReturn(Optional.of(produto1));
		
		cadastrarProduto.remover(produto1.getId());
		Mockito.verify(produtoRepository, times(1)).save(produto1);
	}

}
