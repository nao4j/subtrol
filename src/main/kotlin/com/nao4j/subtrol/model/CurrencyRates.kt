package com.nao4j.subtrol.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate

@Document(collection = "currency_rates")
data class CurrencyRates(
        @Id val id: String? = null,
        val base: String,
        val date: LocalDate,
        val timestamp: Instant,
        val success: Boolean,
        val rates: Map<String, BigDecimal>
)
