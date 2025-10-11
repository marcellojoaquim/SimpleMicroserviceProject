package com.mjsilva.vendas.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mjsilva.vendas.domain.Venda;
import com.mjsilva.vendas.domain.dto.VendaDto;
import com.mjsilva.vendas.repository.CadastrarVenda;
import com.mjsilva.vendas.usecase.BuscarVenda;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendas")
public class VendaController {

	private BuscarVenda buscarVenda;
	private CadastrarVenda cadastrarVenda;
	
	public VendaController(BuscarVenda buscarVenda, CadastrarVenda cadastrarVenda) {
		this.buscarVenda = buscarVenda;
		this.cadastrarVenda = cadastrarVenda;
	}
	
	@GetMapping
	@Operation(summary = "Lista todas as vendas")
	public ResponseEntity<Page<Venda>> buscar(@Parameter(description = "Opbjeto paginacao") Pageable pageable){
		return ResponseEntity.ok(buscarVenda.buscar(pageable));
	}
	
	@GetMapping(value = "/{codigo}")
	@Operation(summary = "Buscar uma venda por codigo")
	public ResponseEntity<Venda> buscarPorCodigo(@PathVariable(required = true) String codigo) {
		return ResponseEntity.ok(buscarVenda.buscarPorCodigo(codigo));
	}
	
	@PostMapping()
	@Operation(summary = "Criar uma venda")
	public ResponseEntity<Venda> cadastrar(@RequestBody @Valid VendaDto vendaDto){
		return ResponseEntity.ok(cadastrarVenda.cadastrar(vendaDto));
	}
	
	
	@PutMapping("/{id}/{codigoProduto}/{quantidade}/addProduto")
	@Operation(summary = "Adicionar produto a venda")
	public ResponseEntity<Venda> adicionarProduto(
			@PathVariable(required = true) String id,
			@PathVariable(required = true) String codigoProduto,
			@PathVariable(required = true) Integer quantidade){
		
		return ResponseEntity.ok(cadastrarVenda.adicionarProduto(id, codigoProduto, quantidade));
		
	}
	
	
}
