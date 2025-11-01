package it.marx.kingfisher.repository.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import it.marx.kingfisher.dto.UserDTO;
import it.marx.kingfisher.exceptions.UserNotFoundException;
import it.marx.kingfisher.repository.IUserRepository;
import it.marx.kingfisher.repository.entity.UserEty;
import lombok.extern.slf4j.Slf4j;

@Repository("userRepositoryMock")
@Slf4j
public class UserRepositoryMock implements IUserRepository {

    @Override
    public UserDTO find(String username, String email) {
        // Verifica che almeno uno dei due parametri sia valorizzato
        if ((username == null || username.isEmpty()) && (email == null || email.isEmpty())) {
            return null;
        }

        try {
            ClassPathResource resource = new ClassPathResource("mock-data/user-mock.csv");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                    CSVParser csvParser = new CSVParser(reader,
                            CSVFormat.DEFAULT.builder()
                                    .setHeader()
                                    .setSkipHeaderRecord(true)
                                    .build())) {

                for (CSVRecord record : csvParser) {
                    String csvId = record.get("id");
                    String csvUsername = record.get("username");
                    String csvEmail = record.get("email");

                    // Logica di filtro
                    boolean usernameMatch = (username == null || username.isEmpty())
                            || csvUsername.equals(username);
                    boolean emailMatch = (email == null || email.isEmpty())
                            || csvEmail.equals(email);

                    if (usernameMatch && emailMatch) {
                        UserEty user = new UserEty();
                        user.setId(csvId);
                        user.setUsername(csvUsername);
                        user.setEmail(csvEmail);
                        return UserDTO.fromEntity(user);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore durante la lettura del file CSV", e);
        }

        log.error("User not found in the database");
        throw new UserNotFoundException("User not found in the database");
    }
}
