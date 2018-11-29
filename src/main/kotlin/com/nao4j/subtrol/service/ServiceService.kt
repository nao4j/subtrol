package com.nao4j.subtrol.service

import com.nao4j.subtrol.dto.ShortService
import com.nao4j.subtrol.security.ADMIN
import com.nao4j.subtrol.security.USER
import org.springframework.security.access.annotation.Secured

interface ServiceService {

    @Secured(value = [USER, ADMIN])
    fun getAllForUser(userId: String): Set<ShortService>

    @Secured(value = [USER, ADMIN])
    fun create(userId: String, service: ShortService): ShortService

    @Secured(value = [USER, ADMIN])
    fun remove(userId: String, service: ShortService): ShortService

}
