package com.mjdsilva.produto.service.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mjdsilva.produto.service.domain.Produto;
import com.mjdsilva.produto.service.domain.Produto.Status;
import com.mjdsilva.produto.service.usecases.BuscarProduto;
import com.mjdsilva.produto.service.usecases.CadastrarProduto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

	private BuscarProduto buscarProduto;
	private CadastrarProduto cadastrarProduto;
	
	public ProdutoController(BuscarProduto buscarProduto, CadastrarProduto cadastrarProduto) {
		this.buscarProduto = buscarProduto;
		this.cadastrarProduto = cadastrarProduto;
	}
	
	@GetMapping
	@Operation(summary = "Buscar todos os produtos")
	public ResponseEntity<Page<Produto>> buscar(@Parameter(description = "Objeto de paginação") Pageable pageable){
		return ResponseEntity.ok(buscarProduto.buscar(pageable));
	}
	
	
	@GetMapping(value="/codigo/{codigo}")
	@Operation(summary = "Buscar produto por codigo")
	public ResponseEntity<Produto> buscarPorCodigo(@PathVariable(value = "codigo", required = true) String codigo){
		return ResponseEntity.ok(buscarProduto.buscarPorCodigo(codigo));
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "Buscar por Id")
	public ResponseEntity<Produto> buscarPorId(@PathVariable(value = "id", required = true) String id){
		return ResponseEntity.ok(buscarProduto.buscarPorId(id));
	}
	
	@GetMapping(value = "/status")
	@Operation(summary = "Buscar por status")
	public ResponseEntity<Page<Produto>> buscarPorStatus(@RequestParam(value = "status", required = true) Status status, 
			@Parameter(description = "Objeto de paginacao") Pageable pageable) {
		return ResponseEntity.ok(buscarProduto.buscarPorStatus(pageable, status));
	}
	
	@PostMapping
	@Operation(summary = "Cadastrar produto")
	public ResponseEntity<Produto> cadastrar(@RequestBody @Valid Produto produto) {
		return ResponseEntity.ok(cadastrarProduto.cadastrar(produto));
	}
	
	@PutMapping
	@Operation(summary = "Atualiza produto")
	public ResponseEntity<Produto> atualizar(@RequestBody @Valid Produto produto) {
		return ResponseEntity.ok(cadastrarProduto.atualizar(produto));
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Remove produto")
	public ResponseEntity<String> remover(@PathVariable(value = "id") String id) {
		cadastrarProduto.remover(id);
		return ResponseEntity.ok("Removido com sucesso.");
	}

}
