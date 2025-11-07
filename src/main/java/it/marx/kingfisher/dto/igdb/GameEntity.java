package it.marx.kingfisher.dto.igdb;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.marx.kingfisher.enums.GameStatusEnum;
import it.marx.kingfisher.enums.GameTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameEntity {

    private Long id; // Unique identifier of the game

    private String name; // Name of the game

    /**
     * URL-friendly identifier for the game (a "slug").
     * Unique identifier used in URLs, composed of lowercase letters and hyphens.
     */
    private String slug;

    private String summary; // Brief description of the game

    private String storyline; // Short summary of the game's plot

    private String url; // Official web address (URL) related to the game

    private String checksum; // Unique hash used to verify data integrity

    @JsonProperty("created_at")
    private Instant createdAt; // Date when the game was added to the IGDB database

    @JsonProperty("updated_at")
    private Instant updatedAt; // Date of the last update to the entry in the IGDB database

    @JsonProperty("first_release_date")
    private Instant firstReleaseDate; // Initial release date of the game (Unix timestamp)

    private Long cover; // ID of the game's cover image

    @JsonProperty("parent_game")
    private Long parentGame; // ID of the main game if this is a DLC or part of a bundle

    @JsonProperty("game_type")
    private GameTypeEnum gameType; // Type of game (e.g., base, expansion, etc.)

    @JsonProperty("game_status")
    private GameStatusEnum gameStatus; // Release status of the game (e.g., released, in development)

    private Integer status; // Deprecated status, use gameStatus instead

    private Integer category; // Deprecated category, use gameType instead

    @JsonProperty("age_ratings")
    private List<Long> ageRatings; // List of IDs for PEGI or similar age ratings

    @JsonProperty("aggregated_rating")
    private Double aggregatedRating; // Average rating from external critics

    @JsonProperty("aggregated_rating_count")
    private Integer aggregatedRatingCount; // Number of external critics who rated

    @JsonProperty("alternative_names")
    private List<Long> alternativeNames; // Alternative names of the game as IDs

    private List<Long> artworks; // IDs of images/artwork associated with the game

    private List<Long> bundles; // IDs of bundles to which the game belongs

    @JsonProperty("external_games")
    private List<Long> externalGames; // IDs corresponding to this game on other platforms/services

    private List<Long> franchises; // IDs of other series or franchises related to the game

    @JsonProperty("game_engines")
    private List<Long> gameEngines; // IDs of graphics engines used in the game

    @JsonProperty("game_modes")
    private List<Long> gameModes; // IDs of game modes (e.g., singleplayer, multiplayer)

    private List<Long> genres; // IDs of game genres

    private Integer hypes; // Number of "hype" or followers before launch

    @JsonProperty("involved_companies")
    private List<Long> involvedCompanies; // IDs of companies involved in development

    private List<Long> keywords; // IDs of keywords/tags associated with the game

    private List<Long> platforms; // IDs of platforms where the game is available

    @JsonProperty("player_perspectives")
    private List<Long> playerPerspectives; // IDs of player perspectives

    @JsonProperty("release_dates")
    private List<Long> releaseDates; // IDs of release dates

    private List<Long> screenshots; // IDs of game screenshots

    @JsonProperty("similar_games")
    private List<Long> similarGames; // IDs of similar games

    private List<Long> tags; // IDs of tags associated with the game

    private List<Long> themes; // IDs of themes present in the game

    private List<Long> videos; // IDs of videos related to the game

    private List<Long> websites; // IDs of websites associated with the game

    private Double rating; // Average rating from IGDB users

    @JsonProperty("rating_count")
    private Integer ratingCount; // Total number of IGDB user ratings

    @JsonProperty("total_rating")
    private Double totalRating; // Overall rating (average of users and critics)

    @JsonProperty("total_rating_count")
    private Integer totalRatingCount; // Total number of user + critic ratings

    @JsonProperty("language_supports")
    private List<Long> languageSupports; // IDs of languages supported in the game

    @JsonProperty("game_localizations")
    private List<Long> gameLocalizations; // IDs of the game's regional localizations

    private List<Long> collections; // IDs of collections to which the game belongs

}
