package com.nao4j.subtrol.repository;

import com.nao4j.subtrol.document.User;
import com.nao4j.subtrol.dto.UserCredentials;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<UserCredentials> findUserCredentialsByEmail(String email);

}
