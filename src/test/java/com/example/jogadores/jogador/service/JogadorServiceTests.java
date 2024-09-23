package com.example.jogadores.jogador.service;


import com.example.jogadores.jogador.model.Jogador;
import com.example.jogadores.jogador.repository.JogadorRepository;
import com.example.jogadores.time.RetornarTImeDTO;
import com.example.jogadores.time.TimeNaoEncontrandoException;
import com.example.jogadores.time.TimeService;
import jakarta.websocket.MessageHandler;
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
public class JogadorServiceTests {


    @InjectMocks
    private JogadorService jogadorService;

    @Mock
    private JogadorRepository jogadorRepository;

    @Mock
    private TimeService timeService;



    @Test
    public void testListarJogadores() {

        // preparo
        Jogador jogador = new Jogador();
        jogador.setNome("Festa");
        jogador.setConvidados(10);
        jogador.setCpf("123");

        List<Jogador> partidas = new ArrayList<>();
        partidas.add(jogador);

        Mockito.when(jogadorRepository.findAll()).thenReturn(partidas);

        // chamada do codigo testado
        List<Jogador> resultado = jogadorService.listar("Festa");

        // verificacao dos resultados
        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(1, resultado.size());
        Assertions.assertEquals("Festa", resultado.getFirst().getNome());
        Assertions.assertEquals(10, resultado.getFirst().getConvidados());
    }
    @Test
    public void testSalvarJogadorNotSucessful() {
        Jogador aposta = new Jogador();
        aposta.setId("1");

        RetornarTImeDTO partidaDTO = new RetornarTImeDTO();
        ResponseEntity<RetornarTImeDTO> responseEntity = new ResponseEntity<>(partidaDTO, HttpStatus.NOT_FOUND);


        Assertions.assertThrows(RuntimeException.class, () -> {
            jogadorService.addTime(aposta, "111");
        });
    }

    @Test
    public void testSalvarJogadorWhenStatusCodeIsNotSuccessful() {
        Jogador jogador = new Jogador();
        jogador.setNome("Festa");
        jogador.setConvidados(10);
        jogador.setCpf("111");
        jogador.setId("1");



        RetornarTImeDTO retornarPartidaDTO = new RetornarTImeDTO();
        ResponseEntity<RetornarTImeDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.NOT_FOUND);

        Mockito.when(timeService.getTime("111")).thenReturn(partidaDto);
        Mockito.when(jogadorRepository.findById("1")).thenReturn(Optional.of(jogador));

        Assertions.assertThrows(TimeNaoEncontrandoException.class, () -> {
            jogadorService.addTime(jogador,"111");

        });
    }
    @Test
    public void testSalvarJogadorWhenMaxGuests() {
        Jogador jogador = new Jogador();
        jogador.setNome("Festa");
        jogador.setConvidados(0);
        jogador.setCpf("111");
        jogador.setId("1");



        RetornarTImeDTO retornarPartidaDTO = new RetornarTImeDTO();
        ResponseEntity<RetornarTImeDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.OK);

        Mockito.when(timeService.getTime("111")).thenReturn(partidaDto);
        Mockito.when(jogadorRepository.findById("1")).thenReturn(Optional.of(jogador));

        Assertions.assertThrows(RuntimeException.class, () -> {
            jogadorService.addTime(jogador,"111");

        });
    }

    @Test
    public void TestCadastrarJogador(){

        Jogador jogador = new Jogador();
        jogador.setNome("Festa");
        jogador.setConvidados(10);
        jogador.setCpf("123");


        RetornarTImeDTO retornarPartidaDTO = new RetornarTImeDTO();
        ResponseEntity<RetornarTImeDTO> partidaDto = new ResponseEntity<>(retornarPartidaDTO, HttpStatus.OK);

        Mockito.when(timeService.getTime("123")).thenReturn(partidaDto);
        Mockito.when(jogadorRepository.save(Mockito.any(Jogador.class))).thenReturn(jogador);



        Jogador retorno = jogadorService.salvar(jogador);

        // Verify the results
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(10, retorno.getConvidados());
        Assertions.assertEquals("Festa", retorno.getNome());
        Assertions.assertEquals("123", retorno.getCpf());


    }




}
