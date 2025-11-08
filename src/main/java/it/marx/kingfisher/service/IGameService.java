package it.marx.kingfisher.service;

import java.util.List;

import it.marx.kingfisher.dto.GameDTO;
import it.marx.kingfisher.dto.igdb.GameEntity;

public interface IGameService {

    GameDTO getGame(Long id);

    List<GameDTO> searchGames(String name);

}
