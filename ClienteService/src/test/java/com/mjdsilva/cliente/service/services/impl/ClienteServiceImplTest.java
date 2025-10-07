package com.mjdsilva.cliente.service.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mjdsilva.cliente.service.model.Cliente;
import com.mjdsilva.cliente.service.model.dto.ClienteDto;
import com.mjdsilva.cliente.service.repository.IClienteRepository;

@ExtendWith(SpringExtension.class)
public class ClienteServiceImplTest {

	@InjectMocks
	CLienteServiceImpl clienteService;
	
	@Mock
	IClienteRepository clienteRepository;
	
	private Cliente cliente;
	private ClienteDto dto;
	private final Long cpf = 123456789l;
	private final Long id = 987654321l;
	
	@BeforeEach
	void setUp() {
		dto = ClienteDto.builder()
				.cpf(123456789l)
				.nome("Primeiro Nome")
				.segundoNome("Segundo Nome")
				.build();
		
		cliente = new Cliente();
		cliente.setCpf(dto.getCpf());
		cliente.setNome(dto.getNome());
		cliente.setSegundoNome(dto.getSegundoNome());
		
	}
	
	@Test
	@DisplayName("Deve retornar um cliente por cpf")
	void buscarPorCpfTest() {
		when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(cliente));
		
		Optional<Cliente> result = clienteService.buscarPorCpf(cpf);
		
		Mockito.verify(clienteRepository, times(1)).findByCpf(cpf);
		assertEquals(cliente.getCpf(), result.get().getCpf());
		assertEquals(cliente.getNome(), result.get().getNome());

	}
	
	@Test
	@DisplayName("Deve retornar um cliente por id")
	void buscarPorIdTest() {
		when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
		
		Optional<Cliente> result = clienteService.buscarPorId(id);
		
		Mockito.verify(clienteRepository, times(1)).findById(id);
		assertEquals(cliente.getCpf(), result.get().getCpf());
	}
	
	@Test
	@DisplayName("Deve cadastrar um cliente")
	void cadastrarTest() {
		when(clienteRepository.save(cliente)).thenReturn(cliente);
		
		Cliente result = clienteService.cadastrar(cliente);
		
		Mockito.verify(clienteRepository, times(1)).save(cliente);
		assertEquals(result.getCpf(), cliente.getCpf());
	}
	
	@Test
	@DisplayName("Deve remover um cliente por id")
	void removerCliente() {
		doNothing().when(clienteRepository).deleteById(id);
		clienteRepository.deleteById(id);
		
		Mockito.verify(clienteRepository, times(1)).deleteById(id);
	}
	
	@Test
	@DisplayName("Deve atualizar um cliente")
	void atualizar() {
		Cliente clienteAtualizado = new Cliente();
		clienteAtualizado.setId(1234567l);
		clienteAtualizado.setNome("Novo nome");
		clienteAtualizado.setCpf(123456789l);
		clienteAtualizado.setSegundoNome("Novo segundo Nome");
		
		when(clienteRepository.save(clienteAtualizado)).thenReturn(clienteAtualizado);
		
		Cliente result = clienteService.atualizar(clienteAtualizado);
		
		assertEquals(clienteAtualizado.getCpf(), result.getCpf());
		assertEquals(clienteAtualizado.getNome(), result.getNome());
	}
}
