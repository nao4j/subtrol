package com.nao4j.subtrol.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Immutable
import org.springframework.data.mongodb.core.mapping.Document

@Immutable
@Document("users")
data class User(
        @Id val id: String?,
        val email: String,
        val password: String,
        val settings: Settings,
        val services: Collection<Service> = emptyList()
)
