package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.ExactPeriod
import com.nao4j.subtrol.model.Subscription

interface SubscriptionSliceService {

    fun slice(subscription: Subscription, period: ExactPeriod): Collection<ExactPeriod>

}
