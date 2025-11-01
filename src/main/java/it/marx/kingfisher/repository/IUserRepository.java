package it.marx.kingfisher.repository;

import it.marx.kingfisher.dto.UserDTO;

public interface IUserRepository {

    UserDTO find(String username, String email);

}
