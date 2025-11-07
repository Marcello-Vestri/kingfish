package it.marx.kingfisher.controller.prova;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.marx.kingfisher.client.IGameClient;
import it.marx.kingfisher.client.IPlatformClient;
import it.marx.kingfisher.dto.igdb.PlatformDTO;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProvaController {

    private IGameClient gameClient;

    private IPlatformClient platformClient;

    public ProvaController(
            IGameClient gameClient,
            IPlatformClient platformClient) {

        this.gameClient = gameClient;
        this.platformClient = platformClient;
    }

    // ENDPOINTS methods

    @GetMapping("/prova")
    public ResponseEntity<PlatformDTO> prova(String name) {

        // List<GameDTO> games = gameClient.searchGamesByName(name);
        PlatformDTO platform = platformClient.getPlatform(170L);
        return ResponseEntity.ok(platform);
    }
}
