package it.marx.kingfisher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class UrlConfig {

    @Value("${igdb.url}")
    private String igdbUrl;

    @Value("${twitch.url}")
    private String twitchUrl;
}
