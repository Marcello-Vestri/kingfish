package it.marx.kingfisher.dto;

import it.marx.kingfisher.repository.entity.UserEty;
import lombok.Data;

@Data
public class UserDTO {

    private String username;

    private String email;

    // Methods
    public static UserDTO fromEntity(UserEty user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
