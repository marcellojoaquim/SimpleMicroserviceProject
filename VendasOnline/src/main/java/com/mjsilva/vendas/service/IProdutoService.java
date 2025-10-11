package com.mjsilva.vendas.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mjsilva.vendas.domain.Produto;

@FeignClient(name = "produto", url = "${application.produtoService.endpointConsultarProduto}")
public interface IProdutoService {

	@GetMapping(
			value = "/codigo/{codigo}",
			produces = "application/json", 
			headers = "application/json")
	Produto buscaProduto(@PathVariable("codigo") String codigoProduto);
}
