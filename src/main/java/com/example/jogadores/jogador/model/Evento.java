package com.example.jogadores.jogador.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Evento {

    @Id
    private String id;
    private String nome;
    private Integer convidados;

    private String cpf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getConvidados() {
        return convidados;
    }

    public void setConvidados(Integer convidados) {
        this.convidados = convidados;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Evento(String id, String nome, Integer convidados, String cpf) {
        this.id = id;
        this.nome = nome;
        this.convidados = convidados;
        this.cpf = cpf;
    }

    public Evento(){
    }

}
