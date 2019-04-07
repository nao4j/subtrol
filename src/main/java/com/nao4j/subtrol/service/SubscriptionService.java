package com.nao4j.subtrol.service;

import com.nao4j.subtrol.document.internal.Subscription;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

import static com.nao4j.subtrol.security.Roles.ADMIN;
import static com.nao4j.subtrol.security.Roles.USER;

public interface SubscriptionService {

    @Secured({USER, ADMIN})
    List<Subscription> getAll(String userId, String serviceName);

    @Secured({USER, ADMIN})
    Subscription add(String userId, String serviceName, Subscription subscription);

    @Secured({USER, ADMIN})
    Subscription remove(String userId, String serviceName, Subscription subscription);

}
