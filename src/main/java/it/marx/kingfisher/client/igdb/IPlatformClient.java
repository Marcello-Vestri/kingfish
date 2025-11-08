package it.marx.kingfisher.client.igdb;

import java.util.List;

import it.marx.kingfisher.dto.igdb.PlatformEntity;

public interface IPlatformClient {

    PlatformEntity getPlatform(Long id);

    List<PlatformEntity> getPlatforms(List<Long> ids);
}
