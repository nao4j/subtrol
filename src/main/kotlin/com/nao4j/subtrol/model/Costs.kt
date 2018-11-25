package com.nao4j.subtrol.model

data class Costs(
        val total: Price,
        val isAccurate: Boolean,
        val period: ExactPeriod,
        val currencyRates: Set<CurrencyRate>,
        val costs: List<Cost>
)
