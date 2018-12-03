package com.nao4j.subtrol.dto

data class UserCredentials(
    val id: String,
    val email: String,
    val password: String,
    val roles: Set<String> = emptySet()
)
