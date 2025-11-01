package it.marx.kingfisher.client.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import it.marx.kingfisher.dto.GameDTO;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GameClient implements IGameClient {

    private static final String GAMES_URL = "https://api.igdb.com/v4/games";
    private static final String SEARCH_URL = "https://api.igdb.com/v4/search";

    private ITwitchClient twitchClient;

    private final RestTemplate restTemplate;

    public GameClient(RestTemplate restTemplate, ITwitchClient twitchClient) {
        this.restTemplate = restTemplate;
        this.twitchClient = twitchClient;
    }

    @Override
    public GameDTO getGame(String id) {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(GAMES_URL)
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            final String body = String.format("fields *; where id = %s;", id);
            log.info(body);

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<GameDTO[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    GameDTO[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                GameDTO[] game = response.getBody();

                log.info("Successfully retrieved game by ID");
                log.debug("Game: {}", game[0]);

                return game[0];
            } else {
                log.warn("Failed to retrieve game. Status: {}", response.getStatusCode());
                return null;
            }

        } catch (RestClientException e) {
            log.error("Error during findGames request", e);
            throw e;
        }
    }

    public List<GameDTO> getGames(List<String> ids) {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(GAMES_URL)
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            List<Integer> idsInt = ids.stream().map(x -> Integer.parseInt(x)).toList();
            Query query = new Query();
            query.addWhereEqualsIn("id", idsInt).setLimit(25);

            String body = query.buildQuery();
            log.info(body);

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<GameDTO[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    GameDTO[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<GameDTO> games = Arrays.asList(response.getBody());

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
    public List<GameDTO> searchGamesByName(String name) {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(SEARCH_URL)
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            Query query = new Query();
            query.addField("id")
                    .addWhereContains("name", name)
                    .setLimit(25);

            String body = query.buildQuery();
            log.info(body);

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Map[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Map[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<Map<String, Integer>> searchResult = Arrays.asList(response.getBody());
                log.info("Successfully retrieved {} games", searchResult.size());
                log.debug("Games: {}", searchResult);

                List<String> ids = searchResult.stream().map(x -> Integer.toString(x.get("id"))).toList();
                return this.getGames(ids);
            } else {
                log.warn("Failed to retrieve games. Status: {}", response.getStatusCode());
                return Collections.emptyList();
            }

        } catch (RestClientException e) {
            log.error("Error during searchGamesByName request", e);
            throw e;
        }
    }

}
