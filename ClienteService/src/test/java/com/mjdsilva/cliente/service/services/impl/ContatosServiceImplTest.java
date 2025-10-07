package com.mjdsilva.cliente.service.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.mjdsilva.cliente.service.model.Contatos;
import com.mjdsilva.cliente.service.model.dto.ContatoResponseDto;
import com.mjdsilva.cliente.service.model.dto.ContatosDto;
import com.mjdsilva.cliente.service.repository.IClienteRepository;
import com.mjdsilva.cliente.service.repository.IContatosRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ContatosServiceImplTest {

    @InjectMocks
    ContatosServiceImpl contatosService;

    @Mock
    IContatosRepository contatosRepository;

    @Mock
    IClienteRepository clienteRepository;

    @Mock
    ModelMapper modelMapper;

    private Contatos contatos;
    private ContatosDto contatosDto;
    private ContatoResponseDto contatoResponseDto;
    private Cliente cliente;
    private final Long ID = 1L;
    private final Long CLIENTE_ID = 123456789L;
    private final String EMAIL_NOVO = "email@email.com";
    private final String EMAIL_EXISTENTE = "existente@test.com";

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(CLIENTE_ID);

        contatosDto = ContatosDto.builder()
                .clienteId(CLIENTE_ID)
                .email(EMAIL_NOVO)
                .tel(123456789l)
                .build();

        contatos = new Contatos();
        contatos.setId(1L);
        contatos.setCliente(cliente);
        contatos.setEmail(contatosDto.getEmail());
        contatos.setTel(contatosDto.getTel());
        
        contatoResponseDto = ContatoResponseDto.builder()
                .email(EMAIL_NOVO)
                .tel(contatosDto.getTel())
                .build();
    }

    @Test
    @DisplayName("Deve cadastrar um contato quando todos os dados são válidos")
    void cadastrar_ComDadosValidos_DeveRetornarContatosDto() {
        
        when(contatosRepository.findByEmail(EMAIL_NOVO)).thenReturn(null);
        when(contatosRepository.findByCliente_Id(CLIENTE_ID)).thenReturn(null);

        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(cliente));

        when(contatosRepository.save(any(Contatos.class))).thenReturn(contatos);

        when(modelMapper.map(any(Contatos.class), eq(ContatosDto.class))).thenReturn(contatosDto);

        ContatosDto resultado = contatosService.cadastrar(contatosDto);

        assertNotNull(resultado);
        assertEquals(EMAIL_NOVO, resultado.getEmail());
        
        verify(contatosRepository, times(1)).findByEmail(EMAIL_NOVO);
        verify(clienteRepository, times(1)).findById(CLIENTE_ID);
        verify(contatosRepository, times(1)).save(any(Contatos.class));
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o e-mail já existe")
    void cadastrar_ComEmailExistente_DeveLancarBusinessException() {
        
        contatosDto = ContatosDto.builder().email(EMAIL_EXISTENTE).build();
        when(contatosRepository.findByEmail(EMAIL_EXISTENTE)).thenReturn(new Contatos());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            contatosService.cadastrar(contatosDto);
        });

        assertEquals("email já existe", exception.getMessage());
        
        verify(contatosRepository, never()).findByCliente_Id(anyLong());
        verify(clienteRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando o Cliente já possui contatos cadastrados")
    void cadastrar_ClienteJaPossuiContatos_DeveLancarBusinessException() {
        when(contatosRepository.findByEmail(EMAIL_NOVO)).thenReturn(null);
        
        when(contatosRepository.findByCliente_Id(CLIENTE_ID)).thenReturn(new Contatos());
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            contatosService.cadastrar(contatosDto);
        });

        assertEquals("Cliente ja possui contatos cadastrados", exception.getMessage());
        verify(clienteRepository, never()).findById(anyLong());
        verify(contatosRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando o Cliente não é encontrado")
    void cadastrar_ClienteNaoEncontrado_DeveLancarEntityNotFoundException() {
        when(contatosRepository.findByEmail(EMAIL_NOVO)).thenReturn(null);
        when(contatosRepository.findByCliente_Id(CLIENTE_ID)).thenReturn(null);
        
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.empty());
        
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            contatosService.cadastrar(contatosDto);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        
        verify(contatosRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Deve atualizar o contato com sucesso e retornar o DTO")
    void atualizar_ComDadosValidos_DeveRetornarContatoResponseDto() {
        when(contatosRepository.findById(ID)).thenReturn(Optional.of(contatos));
        when(contatosRepository.save(any(Contatos.class))).thenReturn(contatos);
        when(modelMapper.map(any(Contatos.class), eq(ContatoResponseDto.class))).thenReturn(contatoResponseDto);

        ContatoResponseDto resultado = contatosService.atualizar(ID, contatosDto);

        assertNotNull(resultado);
        assertEquals(EMAIL_NOVO, resultado.getEmail());
        verify(contatosRepository, times(1)).findById(ID);
        verify(contatosRepository, times(1)).save(contatos);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar atualizar um contato inexistente")
    void atualizar_ComIdInexistente_DeveLancarEntityNotFoundException() {
        when(contatosRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            contatosService.atualizar(ID, contatosDto);
        });

        verify(contatosRepository, never()).save(any());
    }

    // --- TESTES PARA REMOVER ---

    @Test
    @DisplayName("Deve remover o contato com sucesso")
    void remover_ComIdValido_DeveChamarDelete() {
        doNothing().when(contatosRepository).deleteById(ID);

        contatosService.remover(ID);

        verify(contatosRepository, times(1)).deleteById(ID);
    }

    @Test
    @DisplayName("Deve lançar BusinessException ao tentar remover com ID nulo")
    void remover_ComIdNulo_DeveLancarBusinessException() {
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            contatosService.remover(null);
        });

        assertEquals("ID não pode ser nulo", exception.getMessage());
        verify(contatosRepository, never()).deleteById(anyLong());
    }

    // --- TESTES PARA BUSCAR POR ID ---

    @Test
    @DisplayName("Deve buscar o contato por ID com sucesso e retornar o DTO")
    void buscarPorId_ComIdExistente_DeveRetornarContatoResponseDto() {
        when(contatosRepository.findById(ID)).thenReturn(Optional.of(contatos));
        when(modelMapper.map(any(Contatos.class), eq(ContatoResponseDto.class))).thenReturn(contatoResponseDto);

        ContatoResponseDto resultado = contatosService.buscarPorId(ID);

        assertNotNull(resultado);
        verify(contatosRepository, times(1)).findById(ID);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao buscar por ID inexistente")
    void buscarPorId_ComIdInexistente_DeveLancarEntityNotFoundException() {
        when(contatosRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            contatosService.buscarPorId(ID);
        });

        verify(modelMapper, never()).map(any(), any());
    }

    // --- TESTES PARA BUSCAR POR CLIENTE ID ---

    @Test
    @DisplayName("Deve buscar o contato por Cliente ID com sucesso e retornar o DTO")
    void buscarPorClienteId_ComIdExistente_DeveRetornarContatoResponseDto() {
        when(contatosRepository.findByCliente_Id(CLIENTE_ID)).thenReturn(contatos);
        when(modelMapper.map(any(Contatos.class), eq(ContatoResponseDto.class))).thenReturn(contatoResponseDto);

        ContatoResponseDto resultado = contatosService.buscarPorClienteId(CLIENTE_ID);

        assertNotNull(resultado);
        assertEquals(CLIENTE_ID, contatos.getCliente().getId());
        verify(contatosRepository, times(1)).findByCliente_Id(CLIENTE_ID);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao buscar contato por Cliente ID inexistente")
    void buscarPorClienteId_ComIdInexistente_DeveLancarEntityNotFoundException() {
        when(contatosRepository.findByCliente_Id(CLIENTE_ID)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            contatosService.buscarPorClienteId(CLIENTE_ID);
        });

        verify(modelMapper, never()).map(any(), any());
    }
    
    
}
