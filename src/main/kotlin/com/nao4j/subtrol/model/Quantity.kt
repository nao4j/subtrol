package com.nao4j.subtrol.model

data class Quantity(val count: Long, val type: QuantityType) {

    enum class QuantityType {
        UNITS,
        HOURS,
        DAYS,
        MONTHS,
        YEARS
    }

    init {
        if (count <= 0) {
            throw IllegalArgumentException()
        }
    }

}
