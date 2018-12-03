package com.nao4j.subtrol.service.slice

import com.nao4j.subtrol.model.ExactPeriod
import com.nao4j.subtrol.model.RightOpenPeriod
import java.time.LocalDateTime
import java.time.YearMonth

class MonthlySubscriptionSlicer : SubscriptionSlicer {

    override fun slice(
        subscriptionPeriod: RightOpenPeriod,
        calculationPeriod: ExactPeriod,
        stepSize: Long
    ): Collection<ExactPeriod> {
        if (isNotIntersect(subscriptionPeriod, calculationPeriod)) {
            return emptyList()
        }

        val slices = mutableListOf<ExactPeriod>()
        var start = findRealCalculationStart(subscriptionPeriod, stepSize, calculationPeriod)
        while (isBeforeOrEquals(start, calculationPeriod.end) && isBeforeOrEquals(start, subscriptionPeriod.end)) {
            val next = withDay(start.plusMonths(stepSize), subscriptionPeriod.start.dayOfMonth)
            slices.add(ExactPeriod(start, next))
            start = next
        }
        return slices
    }

    private fun isNotIntersect(subscriptionPeriod: RightOpenPeriod, calculatePeriod: ExactPeriod) =
        subscriptionPeriod.end != null && calculatePeriod.start.isAfter(subscriptionPeriod.end)
                || calculatePeriod.end.isBefore(subscriptionPeriod.start)

    private fun isBeforeOrEquals(value: LocalDateTime, reference: LocalDateTime?) =
        reference == null || value.isBefore(reference) || value == reference

    private fun findRealCalculationStart(
        subscriptionPeriod: RightOpenPeriod,
        stepSize: Long,
        calculatePeriod: ExactPeriod
    ): LocalDateTime {
        val subscriptionStartDay = subscriptionPeriod.start.dayOfMonth

        val start = when {
            (subscriptionPeriod.start.isAfter(calculatePeriod.start)) ->
                subscriptionPeriod.start
            (subscriptionStartDay < calculatePeriod.start.dayOfMonth) ->
                withDay(calculatePeriod.start.plusMonths(stepSize), subscriptionStartDay)
            (subscriptionStartDay >= calculatePeriod.start.dayOfMonth) ->
                withDay(calculatePeriod.start, subscriptionStartDay)
            else -> throw RuntimeException()
        }

        val monthsToStart = java.time.Period.between(subscriptionPeriod.start.toLocalDate(), start.toLocalDate()).months
        return start.plusMonths(monthsToStart % stepSize)
    }

    private fun withDay(dateTime: LocalDateTime, day: Int): LocalDateTime {
        val daysInMonth = YearMonth.from(dateTime).lengthOfMonth()
        return if (day < daysInMonth) {
            dateTime.withDayOfMonth(day)
        } else {
            dateTime.withDayOfMonth(daysInMonth)
        }
    }

}
