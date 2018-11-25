package com.nao4j.subtrol.repository

import com.nao4j.subtrol.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String>
