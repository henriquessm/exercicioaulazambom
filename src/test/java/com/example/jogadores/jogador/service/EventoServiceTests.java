package com.example.jogadores.jogador.service;


import com.example.jogadores.jogador.model.Evento;
import com.example.jogadores.jogador.repository.EventoRepository;
import com.example.jogadores.time.RetornarUserDTO;
import com.example.jogadores.time.UserNaoEncontradoException;
import com.example.jogadores.time.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EventoServiceTests {


    @InjectMocks
    private EventoService eventoService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private UserService userService;



    @Test
    public void testListarJogadores() {

        // preparo
        Evento evento = new Evento();
        evento.setNome("Festa");
        evento.setConvidados(10);
        evento.setCpf("123");

        List<Evento> partidas = new ArrayList<>();
        partidas.add(evento);

        Mockito.when(eventoRepository.findAll()).thenReturn(partidas);

        // chamada do codigo testado
        List<Evento> resultado = eventoService.listar("Festa");

        // verificacao dos resultados
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Festa", resultado.getFirst().getNome());
        Assertions.assertEquals(10, resultado.getFirst().getConvidados());
    }
    @Test
    public void testSalvarJogadorNotSucessful() {
        Evento aposta = new Evento();
        aposta.setId("1");

        RetornarUserDTO partidaDTO = new RetornarUserDTO();
        ResponseEntity<RetornarUserDTO> responseEntity = new ResponseEntity<>(partidaDTO, HttpStatus.NOT_FOUND);


        Assertions.assertThrows(RuntimeException.class, () -> {
            eventoService.addTime(aposta, "111");
        });
    }

    @Test
    public void testSalvarJogadorWhenStatusCodeIsNotSuccessful() {
        Evento evento = new Evento();
        evento.setNome("Festa");
        evento.setConvidados(10);
        evento.setCpf("111");
        evento.setId("1");



        RetornarUserDTO retornarPartidaDTO = new RetornarUserDTO();
        ResponseEntity<RetornarUserDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.NOT_FOUND);

        Mockito.when(userService.getTime("111")).thenReturn(partidaDto);
        Mockito.when(eventoRepository.findById("1")).thenReturn(Optional.of(evento));

        Assertions.assertThrows(UserNaoEncontradoException.class, () -> {
            eventoService.addTime(evento,"111");

        });
    }
    @Test
    public void testSalvarJogadorWhenMaxGuests() {
        Evento evento = new Evento();
        evento.setNome("Festa");
        evento.setConvidados(0);
        evento.setCpf("111");
        evento.setId("1");



        RetornarUserDTO retornarPartidaDTO = new RetornarUserDTO();
        ResponseEntity<RetornarUserDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.OK);

        Mockito.when(userService.getTime("111")).thenReturn(partidaDto);
        Mockito.when(eventoRepository.findById("1")).thenReturn(Optional.of(evento));

        Assertions.assertThrows(RuntimeException.class, () -> {
            eventoService.addTime(evento,"111");

        });
    }

    @Test
    public void TestCadastrarJogador(){

        Evento evento = new Evento();
        evento.setNome("Festa");
        evento.setConvidados(10);
        evento.setCpf("123");


        RetornarUserDTO retornarPartidaDTO = new RetornarUserDTO();
        ResponseEntity<RetornarUserDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.OK);

        Mockito.when(userService.getTime("123")).thenReturn(partidaDto);
        Mockito.when(eventoRepository.save(Mockito.any(Evento.class))).thenReturn(evento);



        Evento retorno = eventoService.salvar(evento);

        // Verify the results
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(10, retorno.getConvidados());
        Assertions.assertEquals("Festa", retorno.getNome());
        Assertions.assertEquals("123", retorno.getCpf());


    }




}
