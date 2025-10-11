package com.mjsilva.vendas.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "venda")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Venda {
	
	public enum Status {
		INICIADA, CONCLUIDA, CANCELADA;
		
		public static Status getName(String name) {
			for(Status status: Status.values()) {
				if(status.name().equals(name)) {
					return status;
				}
			}
			return null;
		}
	}
	
	@Id
	private String id;
	
	
	@NotNull
	@Size(min = 4, max = 25)
	@Indexed(unique = true, background = true)
	private String codigo;
	
	@NotNull
	private String clienteId;
	
	@NotNull
	private Status status;
	
	@NotNull
	private Instant dataVenda;
	
	private Set<ProdutoQuantidade> produtos;
	
	private BigDecimal valorTotal;
	
	public Venda() {
		this.produtos = new HashSet<>();
	}
	
	public void adicionarProduto(Produto produto, Integer quantidade) {
		validarStatus();
		Optional<ProdutoQuantidade> optional = produtos.stream()
				.filter(filter -> filter.getProduto().getCodigo().equals(produto.getCodigo())).findAny();
		
		if(optional.isPresent()) {
			ProdutoQuantidade produtoQuantidade = optional.get();
			produtoQuantidade.adicionar(quantidade);
		} else {
			ProdutoQuantidade produtoQuantidade = ProdutoQuantidade.builder()
					.produto(produto)
					.valorTotal(BigDecimal.ZERO)
					.quantidade(0)
					.build();
			
			produtoQuantidade.adicionar(quantidade);
			produtos.add(produtoQuantidade);
		}
		
		recalcularValorTotalVenda();
	}
	
	public void removerProduto(Produto produto, Integer quantidade) {
		validarStatus();
		Optional<ProdutoQuantidade> optional = produtos.stream()
				.filter(filter -> filter.getProduto().getCodigo().equals(produto.getCodigo())).findAny();
		
		if(optional.isPresent()) {
			ProdutoQuantidade produtoQuantidade = optional.get();
			if(produtoQuantidade.getQuantidade() > quantidade) {
				produtoQuantidade.remover(quantidade);
				recalcularValorTotalVenda();
			}
		}
	}

	public void recalcularValorTotalVenda() {
		BigDecimal valorTotal = BigDecimal.ZERO;
		for(ProdutoQuantidade produtoQuantidade: this.produtos) {
			valorTotal = valorTotal.add(produtoQuantidade.getValorTotal());
		}
		
		this.valorTotal = valorTotal;
		
	}

	public void validarStatus() {
		if(this.status == Status.CONCLUIDA || this.status == Status.CANCELADA) {
			throw new UnsupportedOperationException("Impossivel alterar uma venda finalizada ou cancelada.");
		}
		
	}
	
}
