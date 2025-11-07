package it.marx.kingfisher.client;

import it.marx.kingfisher.dto.igdb.PlatformEntity;

public interface IPlatformClient {

    PlatformEntity getPlatform(Long id);
}
