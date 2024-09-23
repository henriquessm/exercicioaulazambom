package com.example.jogadores.jogador.service;


import com.example.jogadores.jogador.model.Evento;
import com.example.jogadores.jogador.repository.EventoRepository;
import com.example.jogadores.time.RetornarUserDTO;
import com.example.jogadores.time.UserNaoEncontradoException;
import com.example.jogadores.time.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UserService userService;


    public Evento salvar(Evento evento){
        //TODO: Checagens
        ResponseEntity<RetornarUserDTO> time = userService.getTime(evento.getCpf());
        if(time.getStatusCode().is2xxSuccessful()){
            RetornarUserDTO timeDTO = time.getBody();
            return eventoRepository.save(evento);

        }
        else {
            throw new UserNaoEncontradoException("Usuário não encontrado");
        }

    }

    public List<Evento> listar(String nome){
        List<Evento> jogadores =  eventoRepository.findAll();
        if  (nome != null) {
            ArrayList<Evento> lista = new ArrayList<>();

            for (Evento partida  : jogadores) {
                if (partida.getNome().equals(nome))  {
                    lista.add(partida);
                }
            }
            return lista;
        }
        List<Evento> response = new ArrayList<>(jogadores);
        return response;
    }


    public Evento addTime(Evento evento, String id){
        //TODO: Checagens2
        Optional<Evento> op = eventoRepository.findById(evento.getId());
        if (op.isEmpty()) {
            throw new RuntimeException("Evento não encontrado");
        }
        Evento eventoA = op.get();
        ResponseEntity<RetornarUserDTO> time = userService.getTime(id);
        if(time.getStatusCode().is2xxSuccessful()){
            RetornarUserDTO timeDTO = time.getBody();
            Integer max = eventoA.getConvidados();
            if(max == 0){
                throw new RuntimeException("Número máximo de convidados atingidos");

            }
            eventoA.setConvidados(eventoA.getConvidados()-1);

            return eventoRepository.save(eventoA);

        }
        else {
            throw new UserNaoEncontradoException("Usuário não encontrado");
        }


    }


}
