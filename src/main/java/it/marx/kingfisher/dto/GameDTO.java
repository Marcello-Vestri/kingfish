package it.marx.kingfisher.dto;

import java.time.Instant;
import java.util.ArrayList;
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

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    @JsonProperty("first_release_date")
    private Instant firstReleaseDate;

    private Long cover;

    @JsonProperty("parent_game")
    private Long parentGame;

    // TODO: da modificare questi due enum, non lo sono più
    @JsonProperty("game_type")
    private Long gameType;

    @JsonProperty("game_status")
    private Long gameStatus;

    private Integer status;

    private Integer category;

    @JsonProperty("age_ratings")
    private List<Long> ageRatings;

    @JsonProperty("aggregated_rating")
    private Double aggregatedRating;

    @JsonProperty("aggregated_rating_count")
    private Integer aggregatedRatingCount;

    @JsonProperty("alternative_names")
    private List<Long> alternativeNames;

    private List<Long> artworks;

    private List<Long> bundles;

    @JsonProperty("external_games")
    private List<Long> externalGames;

    private List<Long> franchises;

    @JsonProperty("game_engines")
    private List<Long> gameEngines;

    @JsonProperty("game_modes")
    private List<Long> gameModes;

    private List<Long> genres;

    private Integer hypes;

    @JsonProperty("involved_companies")
    private List<Long> involvedCompanies;

    private List<Long> keywords;

    private List<PlatformDTO> platforms;

    @JsonProperty("player_perspectives")
    private List<Long> playerPerspectives;

    @JsonProperty("release_dates")
    private List<Long> releaseDates;

    private List<Long> screenshots;

    @JsonProperty("similar_games")
    private List<Long> similarGames;

    private List<Long> tags;

    private List<Long> themes;

    private List<Long> videos;

    private List<Long> websites;

    private Double rating;

    @JsonProperty("rating_count")
    private Integer ratingCount;

    @JsonProperty("total_rating")
    private Double totalRating;

    @JsonProperty("total_rating_count")
    private Integer totalRatingCount;

    @JsonProperty("language_supports")
    private List<Long> languageSupports;

    @JsonProperty("game_localizations")
    private List<Long> gameLocalizations;

    private List<Long> collections;

    // Constructor

    public GameDTO(GameEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.platforms = new ArrayList<>();
        this.gameStatus = entity.getGameStatus();
        this.gameType = entity.getGameType();
    }

}
