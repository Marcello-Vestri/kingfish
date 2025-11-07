package it.marx.kingfisher.service.impl;

import org.springframework.stereotype.Service;

import it.marx.kingfisher.client.IGameClient;
import it.marx.kingfisher.client.IPlatformClient;
import it.marx.kingfisher.dto.GameDTO;
import it.marx.kingfisher.dto.PlatformDTO;
import it.marx.kingfisher.dto.igdb.GameEntity;
import it.marx.kingfisher.dto.igdb.PlatformEntity;
import it.marx.kingfisher.service.IGameService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GameService implements IGameService {

    private IGameClient gameClient;
    private IPlatformClient platformClient;

    GameService(IGameClient gameClient, IPlatformClient platformClient) {
        this.gameClient = gameClient;
        this.platformClient = platformClient;
    }

    @Override
    public GameDTO getGame(Long id) {

        GameEntity gameEntity = gameClient.getGame(id);

        GameDTO game = new GameDTO(gameEntity);

        for (Long platformId : gameEntity.getPlatforms()) {
            PlatformEntity platformEntity = platformClient.getPlatform(platformId);
            PlatformDTO platform = new PlatformDTO(platformEntity);
            game.getPlatforms().add(platform);
        }

        return game;
    }

}
