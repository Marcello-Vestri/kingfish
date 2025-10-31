package it.marx.kingfisher.controller.prova;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.marx.kingfisher.client.IGameClient;
import it.marx.kingfisher.dto.GameDTO;

@RestController
public class ProvaController {

    @Autowired
    private IGameClient gameClient;

    @GetMapping("/prova")
    public ResponseEntity<List<GameDTO>> prova(String name) {

        List<GameDTO> result = gameClient.findGamesByNameContaining(name);

        return ResponseEntity.ok(result);
    }
}
