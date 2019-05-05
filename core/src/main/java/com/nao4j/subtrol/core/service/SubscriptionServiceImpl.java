package com.nao4j.subtrol.core.service;

import com.nao4j.subtrol.core.document.internal.Subscription;
import com.nao4j.subtrol.core.repository.UserRepository;
import com.nao4j.subtrol.currencyrate.service.CurrencyService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final CurrencyService currencyService;

    @Override
    public List<Subscription> getAll(final String userId, final String serviceName) {
        final var user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        final var service = user.getServices().stream()
                .filter(it -> it.getName().equals(serviceName)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
        return service.getSubscriptions().stream()
                .sorted(Comparator.comparing(it -> it.getPeriod().getStart()))
                .collect(toList());
    }

    @Override
    public Subscription add(final String userId, final String serviceName, final Subscription subscription) {
        final var currencies = currencyService.getAll();
        if (!currencies.contains(subscription.getPrice().getCurrencyCode())) {
            throw new IllegalArgumentException();   // todo: message
        }
        final var user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        final var service = user.getServices().stream()
                .filter(it -> it.getName().equals(serviceName)).findFirst()
                .orElseThrow(IllegalArgumentException::new);

        final var intersections = service.getSubscriptions().stream().filter(it -> {
            final var end = it.getPeriod().getEnd();
            return (end != null) && subscription.getPeriod().getStart().isBefore(end);
        }).collect(toList());
        if (!intersections.isEmpty()) {
            throw new IllegalArgumentException();   // todo: message
        }

        final var newSubscriptions = new ArrayList<>(service.getSubscriptions());
        final var previous = service.getSubscriptions().stream()
                .filter(it -> it.getPeriod().getEnd() == null).findFirst()
                .orElse(null);
        if (subscription == previous) {
            throw new IllegalArgumentException();   // todo: message
        }
        if (previous != null) {
            final var updatedPrevious = previous.withPeriod(
                    previous.getPeriod().withEnd(subscription.getPeriod().getStart())
            );
            newSubscriptions.remove(previous);
            newSubscriptions.add(updatedPrevious);
        }
        newSubscriptions.add(subscription);

        final var updatedService = service.withSubscriptions(newSubscriptions);
        final var updatedServices = new ArrayList<>(user.getServices());
        updatedServices.remove(service);
        updatedServices.add(updatedService);
        final var updatedUser = user.withServices(updatedServices);
        userRepository.save(updatedUser);
        return subscription;
    }

    @Override
    public Subscription remove(final String userId, final String serviceName, final Subscription subscription) {
        final var user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        final var service = user.getServices().stream()
                .filter(it -> it.getName().equals(serviceName)).findFirst()
                .orElseThrow(IllegalArgumentException::new);
        if (!service.getSubscriptions().contains(subscription)) {
            throw new IllegalArgumentException();   // todo: message
        }
        final var updatedSubscriptions = new ArrayList<>(service.getSubscriptions());
        updatedSubscriptions.remove(subscription);
        final var updatedService = service.withSubscriptions(updatedSubscriptions);
        final var updatedServices = new ArrayList<>(user.getServices());
        updatedServices.remove(service);
        updatedServices.add(updatedService);
        userRepository.save(user.withServices(updatedServices));
        return subscription;
    }

}
