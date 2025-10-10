package com.mjdsilva.produto.service.resource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjdsilva.produto.service.domain.Produto;
import com.mjdsilva.produto.service.domain.Produto.Status;
import com.mjdsilva.produto.service.usecases.BuscarProduto;
import com.mjdsilva.produto.service.usecases.CadastrarProduto;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockitoBean
	private BuscarProduto buscarProduto;
	
	@MockitoBean
	private CadastrarProduto cadastrarProduto;
	
	private Produto produto;
	private String jsonProduto;
	private final Status statusAtivo = Status.ATIVO;
	private final Status statusInativo = Status.ATIVO;
	
	
	@BeforeEach
	void setUp() throws Exception {
		produto = Produto.builder()
				.id("123456789987654321")
				.nome("Produto 1")
				.codigo("1234A")
				.descricao("Produto teste 01 mais caracteres para ser aprovado")
				.status(statusAtivo)
				.valor(BigDecimal.valueOf(150.00)).build();
		
		jsonProduto = objectMapper.writeValueAsString(produto);
	}
	
	@Test
	@DisplayName("Deve cadastrar um produto")
	void cadastrarPrduto() throws Exception {
		
		when(cadastrarProduto.cadastrar(any(Produto.class))).thenReturn(produto);
		
		mockMvc.perform(post("/produto")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonProduto))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(produto.getId()));
	}
}
