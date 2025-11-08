package it.marx.kingfisher.client.igdb.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
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

import it.marx.kingfisher.client.igdb.ITwitchClient;
import it.marx.kingfisher.config.UrlConfig;
import it.marx.kingfisher.dto.response.TokenResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TwitchClient implements ITwitchClient {

    private static final String TOKEN_PATH = "/oauth2/token";

    @Value("${twitch.client.id}")
    private String clientId;

    @Value("${twitch.client.secret}")
    private String clientSecret;

    private String accessToken;
    private Long expiresAt;

    private final RestTemplate restTemplate;
    private final UrlConfig urlConfig;

    public TwitchClient(RestTemplate restTemplate, UrlConfig urlConfig) {
        this.restTemplate = restTemplate;
        this.urlConfig = urlConfig;
    }

    @PostConstruct
    private void initialize() {
        authenticateClient();
    }

    public void authenticateClient() {
        try {
            URI url = UriComponentsBuilder
                    .fromUriString(urlConfig.getTwitchUrl())
                    .path(TOKEN_PATH)
                    .queryParam("client_id", clientId)
                    .queryParam("client_secret", clientSecret)
                    .queryParam("grant_type", "client_credentials")
                    .build().toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<TokenResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    TokenResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                TokenResponse tokenResponse = response.getBody();
                this.accessToken = tokenResponse.getAccessToken();
                this.expiresAt = System.currentTimeMillis() + (tokenResponse.getExpiresIn() * 1000L);
                log.info("Successfully authenticated with Twitch API. Token expires in {} seconds",
                        tokenResponse.getExpiresIn());
            } else {
                log.error("Failed to authenticate with Twitch API. Status: {}", response.getStatusCode());
                throw new RuntimeException("Failed to authenticate with Twitch API");
            }

        } catch (RestClientException e) {
            log.error("Error during Twitch authentication", e);
            throw new RuntimeException("Error during Twitch authentication", e);
        }
    }

    private boolean isTokenExpired() {
        return expiresAt == null || System.currentTimeMillis() >= expiresAt;
    }

    public String getAccessToken() {
        if (isTokenExpired()) {
            authenticateClient();
        }
        return accessToken;
    }

    public String getClientId() {
        return clientId;
    }
}
