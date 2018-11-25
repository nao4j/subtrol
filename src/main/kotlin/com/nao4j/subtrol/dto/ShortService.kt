package com.nao4j.subtrol.dto

import com.nao4j.subtrol.model.Subscription

data class ShortService(val name: String, val currentSubscription: Subscription? = null)
