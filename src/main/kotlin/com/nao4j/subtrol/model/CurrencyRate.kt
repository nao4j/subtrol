package com.nao4j.subtrol.model

import java.math.BigDecimal

data class CurrencyRate(val source: String, val target: String, val rate: BigDecimal)
