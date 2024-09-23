package com.example.jogadores.time;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    public ResponseEntity<RetornarUserDTO> getTime(String cpf) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(
                "http://184.72.80.215:8080/usuario/" + cpf,
                RetornarUserDTO.class);
    }
}
