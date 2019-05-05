package com.nao4j.subtrol.core.service.slice;

import com.nao4j.subtrol.core.document.internal.ExactPeriod;
import com.nao4j.subtrol.core.document.internal.Quantity.QuantityType;
import com.nao4j.subtrol.core.document.internal.RightOpenPeriod;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;

import static com.nao4j.subtrol.core.document.internal.Quantity.QuantityType.MONTHS;
import static java.time.Period.between;
import static java.util.Collections.emptyList;

@Component
public class MonthlySubscriptionSlicer implements SubscriptionSlicer {

    @Override
    public Collection<ExactPeriod> slice(
            final RightOpenPeriod subscriptionPeriod,
            final ExactPeriod calculationPeriod,
            final long stepSize
    ) {
        if (isNotIntersect(subscriptionPeriod, calculationPeriod)) {
            return emptyList();
        }

        final var slices = new ArrayList<ExactPeriod>();
        var start = findRealCalculationStart(subscriptionPeriod, stepSize, calculationPeriod);
        while (isBeforeOrEquals(start, calculationPeriod.getEnd())
                && isBeforeOrEquals(start, subscriptionPeriod.getEnd())) {
            final var next = withDay(start.plusMonths(stepSize), subscriptionPeriod.getStart().getDayOfMonth());
            slices.add(new ExactPeriod(start, next));
            start = next;
        }
        return slices;
    }

    @Override
    public QuantityType dimension() {
        return MONTHS;
    }

    private boolean isNotIntersect(final RightOpenPeriod subscriptionPeriod, final ExactPeriod calculatePeriod) {
        return (subscriptionPeriod.getEnd() != null)
                && calculatePeriod.getStart().isAfter(subscriptionPeriod.getEnd())
                || calculatePeriod.getEnd().isBefore(subscriptionPeriod.getStart());
    }

    private boolean isBeforeOrEquals(final LocalDateTime value, final LocalDateTime reference) {
        return (reference == null) || value.isBefore(reference) || value.equals(reference);
    }

    private LocalDateTime findRealCalculationStart(
            final RightOpenPeriod subscriptionPeriod,
            final long stepSize,
            final ExactPeriod calculatePeriod
    ) {
        final var subscriptionStartDay = subscriptionPeriod.getStart().getDayOfMonth();

        final LocalDateTime start;
        if (subscriptionPeriod.getStart().isAfter(calculatePeriod.getStart())) {
            start = subscriptionPeriod.getStart();
        } else if (subscriptionStartDay < calculatePeriod.getStart().getDayOfMonth()) {
            start = withDay(calculatePeriod.getStart().plusMonths(stepSize), subscriptionStartDay);
        } else if (subscriptionStartDay >= calculatePeriod.getStart().getDayOfMonth()) {
            start = withDay(calculatePeriod.getStart(), subscriptionStartDay);
        } else {
            throw new IllegalArgumentException();   // todo: message
        }

        final var monthsToStart = between(subscriptionPeriod.getStart().toLocalDate(), start.toLocalDate()).getMonths();
        return start.plusMonths(monthsToStart % stepSize);
    }

    private LocalDateTime withDay(final LocalDateTime dateTime, final int day) {
        final var daysInMonth = YearMonth.from(dateTime).lengthOfMonth();
        if (day < daysInMonth) {
            return dateTime.withDayOfMonth(day);
        } else {
            return dateTime.withDayOfMonth(daysInMonth);
        }
    }

}
