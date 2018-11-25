package com.nao4j.subtrol.model

import java.time.LocalDateTime

data class RightOpenPeriod(val start: LocalDateTime, val end: LocalDateTime?) {

    init {
        if (end != null && end < start) {
            throw IllegalArgumentException()
        }
    }

}

data class ExactPeriod(val start: LocalDateTime, val end: LocalDateTime) {

    init {
        if (end < start) {
            throw IllegalArgumentException()
        }
    }

}
