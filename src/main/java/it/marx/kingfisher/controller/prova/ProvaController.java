package it.marx.kingfisher.controller.prova;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.marx.kingfisher.dto.GameDTO;
import it.marx.kingfisher.service.IGameService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProvaController {

    private IGameService gameService;

    public ProvaController(IGameService gameService) {

        this.gameService = gameService;
    }

    // ENDPOINTS methods

    @GetMapping("/prova")
    public ResponseEntity<List<GameDTO>> prova(String name) {

        return ResponseEntity.ok(gameService.searchGames(name));
    }
}
