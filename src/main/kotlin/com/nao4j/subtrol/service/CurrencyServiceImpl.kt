package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.CurrencyRate
import com.nao4j.subtrol.model.CurrencyRates
import com.nao4j.subtrol.repository.FixerApiRepository
import com.nao4j.subtrol.repository.FixerCacheRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal.ONE
import java.math.RoundingMode
import java.time.LocalDate

@Service
class CurrencyServiceImpl(
        val cacheRepository: FixerCacheRepository,
        val api: FixerApiRepository
): CurrencyService {

    override fun getAll(): Set<String> {
        return api.latest()?.rates?.keys ?: emptySet()
    }

    override fun getRate(source: String, target: String): CurrencyRate {
        val fixerData = cacheRepository.findTopByOrderByTimestampDesc()
        return when {
            (source == fixerData.base) ->
                CurrencyRate(source, target, fixerData.rates[target]!!)
            (target == fixerData.base) ->
                CurrencyRate(
                        source,
                        target,
                        ONE.divide(fixerData.rates[target]!!, 6, RoundingMode.HALF_UP)
                )
            else -> {
                val sourceRate = fixerData.rates[source]!!
                val targetRate = fixerData.rates[target]!!
                CurrencyRate(source, target, targetRate.divide(sourceRate, 6, RoundingMode.HALF_UP))
            }
        }
    }

    @Scheduled(cron = "0 0 3 1/1 * ?")
    override fun loadData(): CurrencyRates? {
        val fixerData = api.latest()
        if (fixerData != null) {
            return cacheRepository.save(fixerData)
        }
        return null
    }

    override fun deleteOldest(date: LocalDate): Long = cacheRepository.deleteAllByDateBefore(date)

}
