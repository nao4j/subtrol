package com.nao4j.subtrol.service

import com.nao4j.subtrol.dto.ShortService

interface ServiceService {

    fun getAllForUser(userId: String): Set<ShortService>

    fun create(userId: String, service: ShortService): ShortService

    fun remove(userId: String, service: ShortService): ShortService

}
