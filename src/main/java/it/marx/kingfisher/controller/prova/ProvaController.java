package it.marx.kingfisher.controller.prova;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.marx.kingfisher.client.IGameClient;
import it.marx.kingfisher.dto.GameDTO;
import it.marx.kingfisher.dto.UserDTO;
import it.marx.kingfisher.repository.IUserRepository;

@RestController
public class ProvaController {

    private IGameClient gameClient;

    private IUserRepository userRepository;

    public ProvaController(
            IGameClient gameClient,
            @Qualifier("userRepositoryMock") IUserRepository userRepository) {

        this.gameClient = gameClient;
        this.userRepository = userRepository;
    }

    // ENDPOINTS methods

    @GetMapping("/prova")
    public ResponseEntity<List<GameDTO>> prova(String name) {

        List<GameDTO> result = gameClient.findGamesByNameContaining(name);
        UserDTO user = userRepository.find("ciccioFranco", null);

        System.out.println(user);

        return ResponseEntity.ok(result);
    }
}
