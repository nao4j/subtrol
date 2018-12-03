package com.nao4j.subtrol.model

data class Cost(
    val name: String,
    val price: Price,
    val period: ExactPeriod,
    val isAccurate: Boolean
)
