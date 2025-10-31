package it.marx.kingfisher.client;

import java.util.List;

import it.marx.kingfisher.dto.GameDTO;

public interface IGameClient {

    List<GameDTO> findGames();

    List<GameDTO> findGamesByNameContaining(String name);

}
