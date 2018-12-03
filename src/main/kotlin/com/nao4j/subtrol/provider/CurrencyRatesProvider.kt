package com.nao4j.subtrol.provider

import com.nao4j.subtrol.model.CurrencyRates

interface CurrencyRatesProvider {

    fun latest(): CurrencyRates?

}
