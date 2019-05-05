package com.nao4j.subtrol.core.service;

import com.nao4j.subtrol.core.document.internal.Subscription;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface SubscriptionService {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    List<Subscription> getAll(String userId, String serviceName);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Subscription add(String userId, String serviceName, Subscription subscription);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Subscription remove(String userId, String serviceName, Subscription subscription);

}
