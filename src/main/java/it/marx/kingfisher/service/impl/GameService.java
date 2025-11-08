package it.marx.kingfisher.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import it.marx.kingfisher.client.IGameClient;
import it.marx.kingfisher.client.IGameStatusClient;
import it.marx.kingfisher.client.IPlatformClient;
import it.marx.kingfisher.dto.GameDTO;
import it.marx.kingfisher.dto.GameStatusDTO;
import it.marx.kingfisher.dto.PlatformDTO;
import it.marx.kingfisher.dto.igdb.GameEntity;
import it.marx.kingfisher.dto.igdb.GameStatusEntity;
import it.marx.kingfisher.dto.igdb.PlatformEntity;
import it.marx.kingfisher.service.IGameService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GameService implements IGameService {

    private IGameClient gameClient;
    private IPlatformClient platformClient;
    private IGameStatusClient gameStatusClient;

    GameService(IGameClient gameClient, IPlatformClient platformClient, IGameStatusClient gameStatusClient) {
        this.gameClient = gameClient;
        this.platformClient = platformClient;
        this.gameStatusClient = gameStatusClient;
    }

    @Override
    public GameDTO getGame(Long id) {

        GameEntity gameEntity = gameClient.getGame(id);
        List<PlatformEntity> platformEntities = platformClient.getPlatforms(gameEntity.getPlatforms());
        GameStatusEntity gameStatusEntity = gameStatusClient.getGameStatus(gameEntity.getGameStatus());

        GameDTO game = new GameDTO(gameEntity);
        game.setGameStatus(new GameStatusDTO(gameStatusEntity));
        game.setPlatforms(
                platformEntities.stream()
                        .map(PlatformDTO::new)
                        .toList());

        return game;
    }

}
