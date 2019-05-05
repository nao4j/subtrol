package com.nao4j.subtrol.core.service;

import com.nao4j.subtrol.core.document.internal.Cost;
import com.nao4j.subtrol.core.document.internal.Costs;
import com.nao4j.subtrol.core.document.internal.ExactPeriod;
import com.nao4j.subtrol.core.document.internal.Price;
import com.nao4j.subtrol.core.document.internal.Service;
import com.nao4j.subtrol.core.document.internal.Subscription;
import com.nao4j.subtrol.core.repository.UserRepository;
import com.nao4j.subtrol.core.service.slice.SubscriptionSliceService;
import com.nao4j.subtrol.currencyrate.dto.CurrencyRate;
import com.nao4j.subtrol.currencyrate.service.CurrencyService;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashSet;

import static com.nao4j.subtrol.core.document.internal.Quantity.QuantityType.UNITS;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class CostServiceImpl implements CostService {

    private final UserRepository userRepository;
    private final SubscriptionSliceService subscriptionSliceService;
    private final CurrencyService currencyService;

    @Override
    public Costs calculateForPeriod(final String userId, final ExactPeriod period) {
        final var user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        final var costs = user.getServices().stream()
                .filter(it -> serviceIn(period, it))
                .flatMap(it -> serviceTo(period, it).stream())
                .map(it -> fixAccurate(user.getSettings().getMainCurrency(), it))
                .sorted(comparing((Cost cost) -> cost.getPeriod().getStart()).thenComparing(Cost::getName))
                .collect(toList());
        final var currencyRates = new HashSet<CurrencyRate>();
        var isAccurate = true;
        var sum = ZERO.setScale(2, HALF_UP);
        for (final Cost cost : costs) {
            if (!cost.isAccurate()) {
                isAccurate = false;
            }
            if (!cost.getPrice().getCurrencyCode().equals(user.getSettings().getMainCurrency())) {
                final var rate = currencyService.getRate(
                        cost.getPrice().getCurrencyCode(),
                        user.getSettings().getMainCurrency()
                );
                currencyRates.add(rate);
                sum = sum.add(cost.getPrice().getSum().multiply(rate.getRate()));
            } else {
                sum = sum.add(cost.getPrice().getSum());
            }
        }
        return new Costs(
                new Price(sum.setScale(2, HALF_UP), user.getSettings().getMainCurrency()),
                isAccurate,
                period,
                currencyRates,
                costs
        );
    }

    private boolean serviceIn(final ExactPeriod period, final Service service) {
        return !service.getSubscriptions().stream().filter(it -> subscriptionIn(period, it)).collect(toList()).isEmpty();
    }

    private boolean subscriptionIn(final ExactPeriod period, final Subscription subscription) {
        final var end = subscription.getPeriod().getEnd();
        return subscription.getPeriod().getStart().isBefore(period.getEnd())
                && (end == null || end.isAfter(period.getStart()));
    }

    private Collection<Cost> serviceTo(final ExactPeriod period, final Service service) {
        return service.getSubscriptions().stream()
                .filter(it -> subscriptionIn(period, it))
                .flatMap(it -> subscriptionTo(service.getName(), period, it).stream())
                .collect(toList());
    }

    private Collection<Cost> subscriptionTo(
            final String serviceName,
            final ExactPeriod period,
            final Subscription subscription
    ) {
        return subscriptionSliceService.slice(subscription, period).stream()
                .map(slice -> {
                    final var isAccurate = subscription.getQuantity().getType() != UNITS;
                    return new Cost(serviceName, subscription.getPrice(), slice, isAccurate);
                }).collect(toList());
    }

    private Cost fixAccurate(final String mainCurrency, final Cost cost) {
        if (!cost.getPrice().getCurrencyCode().equals(mainCurrency)) {
            return cost.withAccurate(false);
        } else {
            return cost;
        }
    }

}
