package it.marx.kingfisher.dto;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.marx.kingfisher.dto.igdb.PlatformEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlatformDTO {

    private Long id;

    private String abbreviation;

    @JsonProperty("alternative_name")
    private String alternativeName;

    @JsonProperty("created_at")
    private Instant createdAt;

    private Integer generation;

    private String name;

    @JsonProperty("platform_family")
    private Long platformFamily;

    @JsonProperty("platform_logo")
    private Long platformLogo;

    @JsonProperty("platform_type")
    private Long platformType;

    private String slug;

    private String summary;

    @JsonProperty("updated_at")
    private Instant updatedAt;

    private String url;

    private List<Long> versions;

    private List<Long> websites;

    // Constructor
    public PlatformDTO(PlatformEntity entity) {
        this.id = entity.getId();
        this.abbreviation = entity.getAbbreviation();
        this.name = entity.getName();
    }

}
