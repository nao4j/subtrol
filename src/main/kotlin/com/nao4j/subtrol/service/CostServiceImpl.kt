package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.Cost
import com.nao4j.subtrol.model.Costs
import com.nao4j.subtrol.model.CurrencyRate
import com.nao4j.subtrol.model.ExactPeriod
import com.nao4j.subtrol.model.Price
import com.nao4j.subtrol.model.Quantity.QuantityType.UNITS
import com.nao4j.subtrol.model.Service
import com.nao4j.subtrol.model.Subscription
import com.nao4j.subtrol.model.User
import com.nao4j.subtrol.repository.UserRepository
import java.math.BigDecimal.ZERO
import java.math.RoundingMode.HALF_UP

@org.springframework.stereotype.Service
class CostServiceImpl(
        val userRepository: UserRepository,
        val subscriptionSliceService: SubscriptionSliceService,
        val currencyService: CurrencyService
): CostService {

    override fun calculateForPeriod(userId: String, period: ExactPeriod): Costs {
        val user: User = userRepository.findById(userId).orElseThrow { IllegalArgumentException() }
        val costs = user.services.filter(serviceIn(period))
                .flatMap(serviceTo(period))
                .map(fixAccurate(user.settings.mainCurrency))
                .sortedWith(compareBy({it.period.start}, {it.name}))
                .toList()
        val currencyRates = mutableSetOf<CurrencyRate>()
        var isAccurate = true
        var sum = ZERO.setScale(2, HALF_UP)
        costs.forEach { cost ->
            if (!cost.isAccurate) {
                isAccurate = false
            }
            sum = if (cost.price.currencyCode != user.settings.mainCurrency) {
                val rate = currencyService.getRate(cost.price.currencyCode, user.settings.mainCurrency)
                currencyRates.add(rate)
                sum.plus(cost.price.sum.multiply(rate.rate))
            } else {
                sum.plus(cost.price.sum)
            }
        }
        return Costs(
                Price(sum.setScale(2, HALF_UP), user.settings.mainCurrency),
                isAccurate,
                period,
                currencyRates,
                costs
        )
    }

    private fun serviceIn(period: ExactPeriod): (Service) -> Boolean = { service ->
        service.subscriptions.filter(subscriptionIn(period)).toList().isNotEmpty()
    }

    private fun subscriptionIn(period: ExactPeriod): (Subscription) -> Boolean = { subscription ->
        subscription.period.start.isBefore(period.end)
                && (subscription.period.end == null || subscription.period.end.isAfter(period.start))
    }

    private fun serviceTo(period: ExactPeriod): (Service) -> Collection<Cost> = { service ->
        service.subscriptions.filter(subscriptionIn(period)).flatMap(subscriptionTo(service.name, period)).toList()
    }

    private fun subscriptionTo(serviceName: String, period: ExactPeriod): (Subscription) -> Collection<Cost> = {
        subscription -> subscriptionSliceService.slice(subscription, period)
            .map { slice ->
                val isAccurate = subscription.quantity.type != UNITS
                Cost(serviceName, subscription.price, slice, isAccurate)
            }
    }

    private fun fixAccurate(mainCurrency: String): (Cost) -> Cost = { cost ->
        if (cost.price.currencyCode != mainCurrency) {
            cost.copy(isAccurate = false)
        } else {
            cost
        }
    }

}
