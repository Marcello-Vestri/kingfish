package it.marx.kingfisher.dto;

import it.marx.kingfisher.dto.igdb.GameStatusEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameStatusDTO {

    private Long id;
    private String status; // Status string

    public GameStatusDTO(GameStatusEntity entity) {
        this.id = entity.getId();
        this.status = entity.getStatus();
    }
}
