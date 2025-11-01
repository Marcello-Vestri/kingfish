package it.marx.kingfisher.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class UserEty {

    @Id
    private String id;

    private String username;

    private String email;
}
