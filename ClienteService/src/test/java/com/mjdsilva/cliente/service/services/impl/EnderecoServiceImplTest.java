package com.mjdsilva.cliente.service.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.mjdsilva.cliente.service.exception.BusinessException;
import com.mjdsilva.cliente.service.model.Cliente;
import com.mjdsilva.cliente.service.model.Endereco;
import com.mjdsilva.cliente.service.model.dto.EnderecoDto;
import com.mjdsilva.cliente.service.model.dto.EnderecoResponseDto;
import com.mjdsilva.cliente.service.repository.IClienteRepository;
import com.mjdsilva.cliente.service.repository.IEnderecoRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceImplTest {

    @InjectMocks
    EnderecoServiceImpl enderecoService;

    @Mock
    IEnderecoRepository enderecoRepository;

    @Mock
    IClienteRepository clienteRepository;

    @Mock
    ModelMapper modelMapper;

    private Endereco endereco;
    private EnderecoDto enderecoDto;
    private EnderecoResponseDto enderecoResponseDto;
    private Cliente cliente;
    private final Long ENDERECO_ID = 1L;
    private final Long CLIENTE_ID = 10L;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(CLIENTE_ID);

        enderecoDto = EnderecoDto.builder()
                .rua("Rua Teste")
                .numero("100")
                .clienteId(CLIENTE_ID)
                .bairro("Bairro")
                .cidade("Cidade")
                .estado("Estado")
                .cep("12345678")
                .build();

        endereco = new Endereco();
        endereco.setId(ENDERECO_ID);
        endereco.setRua(enderecoDto.getRua());
        endereco.setNumero(enderecoDto.getNumero());
        endereco.setIdCliente(cliente);

        enderecoResponseDto = EnderecoResponseDto.builder()
                .rua(enderecoDto.getRua())
                .numero(enderecoDto.getNumero())
                .build();
    }


    @Test
    @DisplayName("Deve cadastrar um novo endereço com sucesso")
    void cadastrar_ComDadosValidos_DeveRetornarEnderecoResponseDto() {
        when(enderecoRepository.findByIdCliente_Id(CLIENTE_ID)).thenReturn(null);
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(cliente));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);
        when(modelMapper.map(any(Endereco.class), eq(EnderecoResponseDto.class))).thenReturn(enderecoResponseDto);

        EnderecoResponseDto resultado = enderecoService.cadastrar(enderecoDto);

        assertNotNull(resultado);
        verify(enderecoRepository, times(1)).save(any(Endereco.class));
    }

    @Test
    @DisplayName("Deve lançar BusinessException se o cliente já tiver endereço cadastrado")
    void cadastrar_ClienteJaPossuiEndereco_DeveLancarBusinessException() {
        when(enderecoRepository.findByIdCliente_Id(CLIENTE_ID)).thenReturn(endereco);

        assertThrows(BusinessException.class, () -> {
            enderecoService.cadastrar(enderecoDto);
        });

        verify(enderecoRepository, never()).save(any());
    }
    

    @Test
    @DisplayName("Deve atualizar o endereço com sucesso e retornar o DTO")
    void atualizar_ComIdExistente_DeveRetornarEnderecoResponseDto() {
        when(enderecoRepository.findById(ENDERECO_ID)).thenReturn(Optional.of(endereco));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);
        when(modelMapper.map(any(Endereco.class), eq(EnderecoResponseDto.class))).thenReturn(enderecoResponseDto);

        EnderecoResponseDto resultado = enderecoService.atualizar(ENDERECO_ID, enderecoDto);

        assertNotNull(resultado);
        verify(enderecoRepository, times(1)).findById(ENDERECO_ID);
        verify(enderecoRepository, times(1)).save(endereco);
    }
    
    @Test
    @DisplayName("Deve lançar NoSuchElementException ao tentar atualizar um endereço inexistente")
    void atualizar_ComIdInexistente_DeveLancarNoSuchElementException() {
        when(enderecoRepository.findById(ENDERECO_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            enderecoService.atualizar(ENDERECO_ID, enderecoDto);
        });

        verify(enderecoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve remover o endereço com sucesso")
    void remover_ComIdValido_DeveChamarDelete() {
        doNothing().when(enderecoRepository).deleteById(ENDERECO_ID);

        enderecoService.remover(ENDERECO_ID);

        verify(enderecoRepository, times(1)).deleteById(ENDERECO_ID);
    }

    @Test
    @DisplayName("Deve lançar BusinessException ao tentar remover com ID nulo")
    void remover_ComIdNulo_DeveLancarBusinessException() {
        assertThrows(BusinessException.class, () -> {
            enderecoService.remover(null);
        });

        verify(enderecoRepository, never()).deleteById(anyLong());
    }


    @Test
    @DisplayName("Deve buscar o endereço por ID com sucesso e retornar o DTO")
    void buscarPorId_ComIdExistente_DeveRetornarEnderecoResponseDto() {
        when(enderecoRepository.findById(ENDERECO_ID)).thenReturn(Optional.of(endereco));
        when(modelMapper.map(any(Endereco.class), eq(EnderecoResponseDto.class))).thenReturn(enderecoResponseDto);

        EnderecoResponseDto resultado = enderecoService.buscarPorId(ENDERECO_ID);

        assertNotNull(resultado);
        verify(enderecoRepository, times(1)).findById(ENDERECO_ID);
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementException ao buscar por ID inexistente")
    void buscarPorId_ComIdInexistente_DeveLancarNoSuchElementException() {
        when(enderecoRepository.findById(ENDERECO_ID)).thenReturn(Optional.empty());
        
        assertThrows(NoSuchElementException.class, () -> {
            enderecoService.buscarPorId(ENDERECO_ID);
        });

        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    @DisplayName("Deve buscar o endereço por Cliente ID com sucesso e retornar o DTO")
    void buscarPorClienteId_ComClienteIdExistente_DeveRetornarEnderecoResponseDto() {
        when(enderecoRepository.findByIdCliente_Id(CLIENTE_ID)).thenReturn(endereco);
        when(modelMapper.map(any(Endereco.class), eq(EnderecoResponseDto.class))).thenReturn(enderecoResponseDto);

        EnderecoResponseDto resultado = enderecoService.buscarPorClienteId(CLIENTE_ID);

        assertNotNull(resultado);
        verify(enderecoRepository, times(1)).findByIdCliente_Id(CLIENTE_ID);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao buscar endereço por Cliente ID inexistente")
    void buscarPorClienteId_ComClienteIdInexistente_DeveLancarEntityNotFoundException() {
        when(enderecoRepository.findByIdCliente_Id(CLIENTE_ID)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            enderecoService.buscarPorClienteId(CLIENTE_ID);
        });

        verify(modelMapper, never()).map(any(), any());
    }

}