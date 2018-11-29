package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.Subscription
import com.nao4j.subtrol.security.ADMIN
import com.nao4j.subtrol.security.USER
import org.springframework.security.access.annotation.Secured

interface SubscriptionService {

    @Secured(value = [USER, ADMIN])
    fun getAll(userId: String, serviceName: String): List<Subscription>

    @Secured(value = [USER, ADMIN])
    fun add(userId: String, serviceName: String, subscription: Subscription): Subscription

    @Secured(value = [USER, ADMIN])
    fun remove(userId: String, serviceName: String, subscription: Subscription): Subscription

}
