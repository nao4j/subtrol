package com.nao4j.subtrol.repository

import com.nao4j.subtrol.model.CurrencyRates
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate

@Repository
class FixerApiRepository(val restTemplate: RestTemplate, @Value("\${api.fixer.key}") val accessKey: String) {

    fun latest(): CurrencyRates? = restTemplate.getForEntity<CurrencyRates>(
            "http://data.fixer.io/api/latest?access_key={accessKey}",
            CurrencyRates::class.java,
            accessKey
    ).body

}
