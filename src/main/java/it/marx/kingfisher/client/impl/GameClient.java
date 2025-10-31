package it.marx.kingfisher.client.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import it.marx.kingfisher.enums.GameStatusEnum;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GameClient implements IGameClient {

    private static final String GAMES_URL = "https://api.igdb.com/v4/games";

    @Autowired
    private ITwitchClient twitchClient;

    private final RestTemplate restTemplate;

    public GameClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<GameDTO> findGames() {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(GAMES_URL)
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            final String body = "fields *; where name = \"Rival Species\"; limit 10;";

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<GameDTO[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    GameDTO[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                GameDTO[] gamesArray = response.getBody();
                List<GameDTO> games = Arrays.asList(gamesArray);

                log.info("Successfully retrieved {} games", games.size());
                log.debug("Games: {}", games);

                return games;
            } else {
                log.warn("Failed to retrieve games. Status: {}", response.getStatusCode());
                return Collections.emptyList();
            }

        } catch (RestClientException e) {
            log.error("Error during findGames request", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<GameDTO> findGamesByNameContaining(String name) {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(GAMES_URL)
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", twitchClient.getClientId());
            headers.set("Authorization", "Bearer " + twitchClient.getAccessToken());
            headers.setContentType(MediaType.TEXT_PLAIN);

            Query query = new Query();
            query.addWhereCondition("name", Query.WhereOperator.CONTAINS, name, Query.LogicalOperator.OR)
                    .addWhereCondition("summary", Query.WhereOperator.CONTAINS, name, Query.LogicalOperator.OR)
                    .addWhereCondition("storyline", Query.WhereOperator.CONTAINS, name, Query.LogicalOperator.OR)
                    .addWhereEquals("game_status", GameStatusEnum.RELEASED.codeInt)
                    .setLimit(10);

            String body = query.buildQuery();

            log.info(body);

            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<GameDTO[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    GameDTO[].class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                GameDTO[] gamesArray = response.getBody();
                List<GameDTO> games = Arrays.asList(gamesArray);

                log.info("Successfully retrieved {} games", games.size());
                log.debug("Games: {}", games);

                return games;
            } else {
                log.warn("Failed to retrieve games. Status: {}", response.getStatusCode());
                return Collections.emptyList();
            }

        } catch (RestClientException e) {
            log.error("Error during findGames request", e);
            return Collections.emptyList();
        }
    }

}
