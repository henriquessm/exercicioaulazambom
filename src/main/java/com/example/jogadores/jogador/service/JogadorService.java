package com.example.jogadores.jogador.service;


import com.example.jogadores.jogador.model.Jogador;
import com.example.jogadores.jogador.repository.JogadorRepository;
import com.example.jogadores.time.RetornarTImeDTO;
import com.example.jogadores.time.TimeNaoEncontrandoException;
import com.example.jogadores.time.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private TimeService timeService;


    public Jogador salvar(Jogador jogador){
        //TODO: Checagens
        ResponseEntity<RetornarTImeDTO> time = timeService.getTime(jogador.getCpf());
        if(time.getStatusCode().is2xxSuccessful()){
            RetornarTImeDTO timeDTO = time.getBody();
            return jogadorRepository.save(jogador);

        }
        else {
            throw new TimeNaoEncontrandoException("Usuário não encontrado");
        }

    }

    public List<Jogador> listar(String nome){
        List<Jogador> jogadores =  jogadorRepository.findAll();
        if  (nome != null) {
            ArrayList<Jogador> lista = new ArrayList<>();

            for (Jogador partida  : jogadores) {
                if (partida.getNome().equals(nome))  {
                    lista.add(partida);
                }
            }
            return lista;
        }
        List<Jogador> response = new ArrayList<>(jogadores);
        return response;
    }


    public Jogador addTime(Jogador jogador, String id){
        //TODO: Checagens2
        Optional<Jogador> op = jogadorRepository.findById(jogador.getId());
        if (op.isEmpty()) {
            throw new RuntimeException("Evento não encontrado");
        }
        Jogador jogadorA = op.get();
        ResponseEntity<RetornarTImeDTO> time = timeService.getTime(id);
        if(time.getStatusCode().is2xxSuccessful()){
            RetornarTImeDTO timeDTO = time.getBody();
            Integer max = jogador.getConvidados();
            if(max == 0){
                throw new RuntimeException("Número máximo de convidados atingidos");

            }
            jogadorA.setConvidados(jogadorA.getConvidados()-1);

            return jogadorRepository.save(jogadorA);

        }
        else {
            throw new TimeNaoEncontrandoException("Usuário não encontrado");
        }


    }


}
