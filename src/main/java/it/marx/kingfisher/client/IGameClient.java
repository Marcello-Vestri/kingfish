package it.marx.kingfisher.client;

import java.util.List;

import it.marx.kingfisher.dto.GameDTO;

public interface IGameClient {

    GameDTO getGame(String id);

    List<GameDTO> getGames(List<String> ids);

    List<GameDTO> searchGamesByName(String name);

}
