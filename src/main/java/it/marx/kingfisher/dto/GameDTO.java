package it.marx.kingfisher.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.marx.kingfisher.dto.igdb.GameEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameDTO {

    private Long id;

    private String name;

    private String slug;

    private String summary;

    private String storyline;

    private String url;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant firstReleaseDate;

    private Long cover;

    private Long parentGame;

    // TODO: da modificare questi due enum, non lo sono più
    private Long gameType;

    private GameStatusDTO gameStatus;

    private Integer status;

    private Integer category;

    private List<Long> ageRatings;

    private Double aggregatedRating;

    private Integer aggregatedRatingCount;

    private List<Long> alternativeNames;

    private List<Long> artworks;

    private List<Long> bundles;

    private List<Long> externalGames;

    private List<Long> franchises;

    private List<Long> gameEngines;

    private List<Long> gameModes;

    private List<Long> genres;

    private Integer hypes;

    private List<Long> involvedCompanies;

    private List<Long> keywords;

    private List<PlatformDTO> platforms;

    private List<Long> playerPerspectives;

    private List<Long> releaseDates;

    private List<Long> screenshots;

    private List<Long> similarGames;

    private List<Long> tags;

    private List<Long> themes;

    private List<Long> videos;

    private List<Long> websites;

    private Double rating;

    private Integer ratingCount;

    private Double totalRating;

    private Integer totalRatingCount;

    private List<Long> languageSupports;

    private List<Long> gameLocalizations;

    private List<Long> collections;

    // Constructor

    public GameDTO(GameEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.platforms = new ArrayList<>();
        this.gameStatus = null;
        this.gameType = entity.getGameType();
        this.rating = entity.getRating();
        this.ratingCount = entity.getRatingCount();
    }

}
