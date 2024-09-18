package com.example.jogadores.time;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TimeService {

    public ResponseEntity<RetornarTImeDTO> getTime(String idTime) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(
                "http://localhost:8082/tjime/" + idTime,
                RetornarTImeDTO.class);
    }
}
