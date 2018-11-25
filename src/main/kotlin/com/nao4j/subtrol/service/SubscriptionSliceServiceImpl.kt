package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.ExactPeriod
import com.nao4j.subtrol.model.Quantity.QuantityType
import com.nao4j.subtrol.model.Quantity.QuantityType.MONTHS
import com.nao4j.subtrol.model.Subscription
import com.nao4j.subtrol.service.slicer.MonthlySubscriptionSlicer
import com.nao4j.subtrol.service.slicer.SubscriptionSlicer
import org.springframework.stereotype.Service

@Service
class SubscriptionSliceServiceImpl : SubscriptionSliceService {

    private val slicers: Map<QuantityType, SubscriptionSlicer>

    init {
        // todo: add implementations for other types
        this.slicers = mapOf(Pair(MONTHS, MonthlySubscriptionSlicer()))
    }

    override fun slice(subscription: Subscription, period: ExactPeriod): Collection<ExactPeriod> {
        if (subscription.quantity.type == MONTHS) {
            return slicers[MONTHS]!!.slice(subscription.period, period, subscription.quantity.count)
        }
        return emptyList()
    }

}
