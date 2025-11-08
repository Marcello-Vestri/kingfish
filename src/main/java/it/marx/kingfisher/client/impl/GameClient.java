package it.marx.kingfisher.client.impl;

import java.net.URI;
import java.util.ArrayList;
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

import it.marx.kingfisher.client.IGameClient;
import it.marx.kingfisher.client.ITwitchClient;
import it.marx.kingfisher.client.sql.Query;
import it.marx.kingfisher.config.UrlConfig;
import it.marx.kingfisher.dto.igdb.GameEntity;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GameClient implements IGameClient {

    private static final String GAMES_PATH = "/v4/games";

    private ITwitchClient twitchClient;

    private final UrlConfig urlConfig;

    private final RestTemplate restTemplate;

    public GameClient(RestTemplate restTemplate, ITwitchClient twitchClient, UrlConfig twitchConfig) {
        this.restTemplate = restTemplate;
        this.twitchClient = twitchClient;
        this.urlConfig = twitchConfig;
    }

    @Override
    public GameEntity getGame(Long id) {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(urlConfig.getIgdbUrl())
                    .path(GAMES_PATH)
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            Query query = new Query();
            query.addWhereEquals("id", id);
            final String body = query.buildQuery();
            log.debug("IGDB game query body: {}", body);

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<GameEntity[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    GameEntity[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                GameEntity[] game = response.getBody();
                if (game.length == 1) {
                    log.info("Successfully retrieved game by ID");
                    log.debug("Game: {}", game[0]);

                    return game[0];
                }
            }

            log.warn("Failed to retrieve game. Status: {}", response.getStatusCode());
            return null;

        } catch (RestClientException e) {
            log.error("Error during findGames request", e);
            throw e;
        }
    }

    @Override
    public List<GameEntity> getGames(List<String> ids) {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(urlConfig.getIgdbUrl())
                    .path(GAMES_PATH)
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            List<Integer> idsInt = ids.stream().map(x -> Integer.parseInt(x)).toList();
            Query query = new Query();
            query.addWhereEqualsIn("id", idsInt);

            String body = query.buildQuery();
            log.info(body);

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<GameEntity[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    GameEntity[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<GameEntity> games = Arrays.asList(response.getBody());

                log.info("Successfully retrieved {} games", Integer.toString(games.size()));
                log.debug("Games: {}", games);

                return games;
            } else {
                log.warn("Failed to retrieve game. Status: {}", response.getStatusCode());
                return new ArrayList<>();
            }

        } catch (RestClientException e) {
            log.error("Error during findGames request", e);
            throw e;
        }
    }

    @Override
    public List<GameEntity> searchGamesByName(String name) {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(urlConfig.getIgdbUrl())
                    .path(GAMES_PATH)
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            Query query = new Query();
            query.setSearch(name)
                    .setLimit(25);

            String body = query.buildQuery();
            log.info(body);

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<GameEntity[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    GameEntity[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<GameEntity> games = Arrays.asList(response.getBody());

                log.info("Successfully retrieved {} games", Integer.toString(games.size()));
                log.debug("Games: {}", games);

                return games;
            } else {
                log.warn("Failed to retrieve game. Status: {}", response.getStatusCode());
                return new ArrayList<>();
            }

        } catch (RestClientException e) {
            log.error("Error during searchGamesByName request", e);
            throw e;
        }
    }

}
