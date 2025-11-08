package it.marx.kingfisher.dto.igdb;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameStatusEntity {

    private Long id;
    private String checksum; // Hash of the object
    private Instant createdAt; // Date this was initially added to the IGDB database
    private String status; // Status string
    private Instant updatedAt; // The last date this entry was updated in the IGDB database
}
