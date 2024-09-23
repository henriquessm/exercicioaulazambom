package com.example.jogadores.jogador.repository;


import com.example.jogadores.jogador.model.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends MongoRepository<Evento, String> {
}
