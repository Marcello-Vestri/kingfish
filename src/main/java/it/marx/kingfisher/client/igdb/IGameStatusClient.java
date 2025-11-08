package it.marx.kingfisher.client.igdb;

import java.util.List;

import it.marx.kingfisher.dto.igdb.GameStatusEntity;

public interface IGameStatusClient {

    GameStatusEntity getGameStatus(Long id);

    List<GameStatusEntity> getGameStatuses(List<Long> ids);

}
