package com.nao4j.subtrol.repository

import com.nao4j.subtrol.dto.UserCredentials
import com.nao4j.subtrol.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {

    fun findUserCredentialsByEmail(email: String?): UserCredentials?

}
