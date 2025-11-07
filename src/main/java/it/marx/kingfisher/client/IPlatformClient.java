package it.marx.kingfisher.client;

import it.marx.kingfisher.dto.igdb.PlatformDTO;

public interface IPlatformClient {

    PlatformDTO getPlatform(Long id);
}
