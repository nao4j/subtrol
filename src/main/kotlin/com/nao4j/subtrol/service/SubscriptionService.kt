package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.Subscription

interface SubscriptionService {

    fun getAll(userId: String, serviceName: String): List<Subscription>

    fun add(userId: String, serviceName: String, subscription: Subscription): Subscription

    fun remove(userId: String, serviceName: String, subscription: Subscription): Subscription

}
