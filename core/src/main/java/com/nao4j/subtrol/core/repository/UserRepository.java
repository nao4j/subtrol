package com.nao4j.subtrol.core.repository;

import com.nao4j.subtrol.core.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
