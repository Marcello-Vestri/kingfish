package it.marx.kingfisher.client;

import java.util.List;

import it.marx.kingfisher.dto.igdb.GameEntity;

public interface IGameClient {

    GameEntity getGame(Long id);

    List<GameEntity> getGames(List<String> ids);

    List<GameEntity> searchGamesByName(String name);

}
