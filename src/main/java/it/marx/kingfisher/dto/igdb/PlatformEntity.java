package it.marx.kingfisher.dto.igdb;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlatformEntity {

    // Unique ID of the platform
    private Long id;

    // Short abbreviation of the platform name (e.g., PS4, XB1)
    private String abbreviation;

    // Alternative or unofficial name for the platform
    @JsonProperty("alternative_name")
    private String alternativeName;

    // Unique hash identifying this platform record
    private String checksum;

    // Date when this platform was first added to IGDB
    @JsonProperty("created_at")
    private Instant createdAt;

    // Numeric generation identifier (e.g., 8 for PS4 generation)
    private Integer generation;

    // Official name of the platform (e.g., PlayStation 4)
    private String name;

    // Reference ID to the platform family (e.g., PlayStation family)
    @JsonProperty("platform_family")
    private Long platformFamily;

    // Reference ID to the logo of the first platform version
    @JsonProperty("platform_logo")
    private Long platformLogo;

    // Reference ID to the platform type (e.g., console, handheld)
    @JsonProperty("platform_type")
    private Long platformType;

    // URL-friendly, unique slug (lowercase version of the name)
    private String slug;

    // Short text summarizing the first version of the platform
    private String summary;

    // Date when the record was last updated
    @JsonProperty("updated_at")
    private Instant updatedAt;

    // Official or main website URL of the platform
    private String url;

    // IDs of the platform’s associated versions
    private List<Long> versions;

    // IDs of the platform’s official or related websites
    private List<Long> websites;
}
