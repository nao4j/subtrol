package com.nao4j.subtrol.service;

import com.nao4j.subtrol.document.internal.Service;
import com.nao4j.subtrol.dto.ShortService;
import com.nao4j.subtrol.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Set;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    private final UserRepository userRepository;

    @Override
    public Set<ShortService> getAllForUser(final String userId) {
        final var user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        return user.getServices().stream().map(service -> {
            final var currentSubscription = service.getSubscriptions().stream().filter(subscription -> {
                final var now = LocalDateTime.now();
                final var start = subscription.getPeriod().getStart();
                final var end = subscription.getPeriod().getEnd();
                return (start.isBefore(now) || start.equals(now)) && ((end == null) || end.isAfter(now));
            }).findFirst().orElse(null);
            return new ShortService(service.getName(), currentSubscription);
        }).collect(toSet());
    }

    @Override
    public ShortService create(final String userId, final ShortService service) {
        final var user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        if (user.getServices().stream().map(Service::getName).collect(toSet()).contains(service.getName())) {
            throw new IllegalArgumentException();
        }
        final var services = new ArrayList<>(user.getServices());
        services.add(new Service(service.getName(), emptyList()));
        final var updatedUser = user.withServices(services);
        userRepository.save(updatedUser);
        return service;
    }

    @Override
    public ShortService remove(final String userId, final ShortService service) {
        final var user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        final var fullService = user.getServices().stream()
                .filter(it -> it.getName().equals(service.getName())).findFirst()
                .orElseThrow(IllegalArgumentException::new);
        final var services = new ArrayList<>(user.getServices());
        services.remove(fullService);
        final var updatedUser = user.withServices(services);
        userRepository.save(updatedUser);
        return service;
    }

}
