package com.example.jogadores.time;


public class RetornarUserDTO {

    private String cpf;
    private String nome;

    public RetornarUserDTO() {
    }
    public RetornarUserDTO(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }
    public String getIdentificador() {
        return cpf;
    }

    public void setIdentificador(String identificador) {
        this.cpf = identificador;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
