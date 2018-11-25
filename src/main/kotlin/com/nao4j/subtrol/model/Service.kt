package com.nao4j.subtrol.model

data class Service(val name: String, val subscriptions: Collection<Subscription> = emptyList())
