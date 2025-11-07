package it.marx.kingfisher.client.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import it.marx.kingfisher.client.IPlatformClient;
import it.marx.kingfisher.client.ITwitchClient;
import it.marx.kingfisher.client.sql.Query;
import it.marx.kingfisher.config.TwitchConfig;
import it.marx.kingfisher.dto.igdb.PlatformEntity;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PlatformClient implements IPlatformClient {

    private static final String PLATFORMS_PATH = "/v4/platforms";

    private final ITwitchClient twitchClient;
    private final TwitchConfig twitchConfig;
    private final RestTemplate restTemplate;

    public PlatformClient(RestTemplate restTemplate, ITwitchClient twitchClient, TwitchConfig twitchConfig) {
        this.restTemplate = restTemplate;
        this.twitchClient = twitchClient;
        this.twitchConfig = twitchConfig;
    }

    @Override
    public PlatformEntity getPlatform(Long id) {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(twitchConfig.getIgdbUrl())
                    .path(PLATFORMS_PATH)
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            Query query = new Query();
            query.addWhereEquals("id", id);
            final String body = query.buildQuery();
            log.info("IGDB platform query body: {}", body);

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<PlatformEntity[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    PlatformEntity[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                PlatformEntity[] platforms = response.getBody();
                if (platforms.length == 1) {
                    log.info("Successfully retrieved platform by ID {}", id);
                    log.debug("Platform: {}", platforms[0]);
                    return platforms[0];
                }
            }

            log.warn("Failed to retrieve platform with ID {}. Status: {}", id, response.getStatusCode());
            return null;

        } catch (RestClientException e) {
            log.error("Error during getPlatform request", e);
            throw e;
        }
    }

    @Override
    public List<PlatformEntity> getPlatforms(List<Long> ids) {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(twitchConfig.getIgdbUrl())
                    .path(PLATFORMS_PATH)
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            Query query = new Query();
            query.addWhereEqualsIn("id", ids);
            final String body = query.buildQuery();
            log.info("IGDB platform query body: {}", body);

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<PlatformEntity[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    PlatformEntity[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                PlatformEntity[] platforms = response.getBody();
                return Arrays.asList(platforms);
            }

            log.warn("Failed to retrieve platforms. Status: {}", response.getStatusCode());
            return null;

        } catch (RestClientException e) {
            log.error("Error during getPlatform request", e);
            throw e;
        }
    }

}
