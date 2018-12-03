package com.nao4j.subtrol.service.slice

import com.nao4j.subtrol.model.ExactPeriod
import com.nao4j.subtrol.model.RightOpenPeriod

interface SubscriptionSlicer {

    fun slice(
        subscriptionPeriod: RightOpenPeriod,
        calculationPeriod: ExactPeriod,
        stepSize: Long
    ): Collection<ExactPeriod>

}
