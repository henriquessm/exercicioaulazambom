package com.example.jogadores.jogador.controller;


import com.example.jogadores.jogador.model.Evento;
import com.example.jogadores.jogador.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evento")
public class EventoController {


    @Autowired
    private EventoService eventoService;

    @GetMapping()
    public List<Evento> listar(@RequestParam(required = false) String nome) {
        return eventoService.listar(nome);
    }

    @PostMapping
    public Evento salvar(@RequestBody Evento aposta) {
        return eventoService.salvar(aposta);
    }

    @PostMapping("/{cpf}")
    public Evento salvarComTime(@RequestBody Evento aposta, @PathVariable String cpf) {
        return eventoService.addTime(aposta, cpf);
    }
}
