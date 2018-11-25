package com.nao4j.subtrol.model

import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class Price(val sum: BigDecimal, val currencyCode: String) {

    init {
        if (sum <= ZERO) {
            throw IllegalArgumentException()
        }
    }

}
