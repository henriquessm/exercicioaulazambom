package com.example.jogadores.jogador.controller;


import com.example.jogadores.jogador.model.Jogador;
import com.example.jogadores.jogador.service.JogadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evento")
public class JogadorController {


    @Autowired
    private JogadorService jogadorService;

    @GetMapping()
    public List<Jogador> listar(@RequestParam(required = false) String nome) {
        return jogadorService.listar(nome);
    }

    @PostMapping
    public Jogador salvar(@RequestBody Jogador aposta) {
        return jogadorService.salvar(aposta);
    }

    @PostMapping("/{cpf}")
    public Jogador salvarComTime(@RequestBody Jogador aposta, @PathVariable String cpf) {
        return jogadorService.addTime(aposta, cpf);
    }
}
