package com.nao4j.subtrol.repository

import com.nao4j.subtrol.model.CurrencyRates
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate

interface FixerCacheRepository : MongoRepository<CurrencyRates, String> {

    fun findTopByOrderByTimestampDesc(): CurrencyRates

    fun deleteAllByDateBefore(date: LocalDate): Long

}
