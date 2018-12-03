package com.nao4j.subtrol.provider

import com.nao4j.subtrol.model.CurrencyRates
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class CurrencyRatesProviderFixerApi(
        val restTemplate: RestTemplate,
        @Value("\${currencyRatesProvider.fixer.key}") val accessKey: String
): CurrencyRatesProvider {

    override fun latest(): CurrencyRates? = restTemplate.getForEntity<CurrencyRates>(
            "http://data.fixer.io/currencyRatesProvider/latest?access_key={accessKey}",
            CurrencyRates::class.java,
            accessKey
    ).body

}
