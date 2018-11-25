package com.nao4j.subtrol.model

import com.nao4j.subtrol.model.Quantity.QuantityType.UNITS

data class Subscription(val price: Price, val period: RightOpenPeriod, val quantity: Quantity) {

    init {
        if (quantity.type == UNITS && period.end == null) {
            throw IllegalAccessException()
        }
        if (quantity.type == UNITS && period.end == null) {
            throw IllegalAccessException()
        }
    }

}
