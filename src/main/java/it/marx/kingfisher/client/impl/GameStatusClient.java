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

import it.marx.kingfisher.client.IGameStatusClient;
import it.marx.kingfisher.client.ITwitchClient;
import it.marx.kingfisher.client.sql.Query;
import it.marx.kingfisher.config.UrlConfig;
import it.marx.kingfisher.dto.igdb.GameStatusEntity;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GameStatusClient implements IGameStatusClient {

    private static final String GAME_STATUSES_PATH = "/v4/game_statuses";

    private final ITwitchClient twitchClient;
    private final UrlConfig urlConfig;
    private final RestTemplate restTemplate;

    public GameStatusClient(RestTemplate restTemplate, ITwitchClient twitchClient, UrlConfig twitchConfig) {
        this.restTemplate = restTemplate;
        this.twitchClient = twitchClient;
        this.urlConfig = twitchConfig;
    }

    @Override
    public GameStatusEntity getGameStatus(Long id) {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(urlConfig.getIgdbUrl())
                    .path(GAME_STATUSES_PATH)
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            Query query = new Query();
            query.addWhereEquals("id", id);
            final String body = query.buildQuery();
            log.info("IGDB game status query body: {}", body);

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<GameStatusEntity[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    GameStatusEntity[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                GameStatusEntity[] gameStatuses = response.getBody();
                if (gameStatuses.length == 1) {
                    log.info("Successfully retrieved game status by ID {}", id);
                    log.debug("GameStatus: {}", gameStatuses[0]);
                    return gameStatuses[0];
                }
            }

            log.warn("Failed to retrieve game status with ID {}. Status: {}", id, response.getStatusCode());
            return null;

        } catch (RestClientException e) {
            log.error("Error during getGameStatus request", e);
            throw e;
        }
    }

    @Override
    public List<GameStatusEntity> getGameStatuses(List<Long> ids) {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(urlConfig.getIgdbUrl())
                    .path(GAME_STATUSES_PATH)
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            Query query = new Query();
            query.addWhereEqualsIn("id", ids);
            final String body = query.buildQuery();
            log.info("IGDB game statuses query body: {}", body);

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<GameStatusEntity[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    GameStatusEntity[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                GameStatusEntity[] gameStatuses = response.getBody();
                return Arrays.asList(gameStatuses);
            }

            log.warn("Failed to retrieve game statuses. Status: {}", response.getStatusCode());
            return null;

        } catch (RestClientException e) {
            log.error("Error during getGameStatuses request", e);
            throw e;
        }
    }

}
