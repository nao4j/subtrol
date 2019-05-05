package com.nao4j.subtrol.core.controller;

import com.nao4j.subtrol.core.document.internal.Subscription;
import com.nao4j.subtrol.core.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public List<Subscription> getAll(@RequestParam final String serviceName, @AuthenticationPrincipal String userId) {
        return subscriptionService.getAll(userId, serviceName);
    }

    @PostMapping
    private Subscription add(
            @RequestParam final String serviceName,
            @RequestBody final Subscription subscription,
            @AuthenticationPrincipal String userId
    ) {
        return subscriptionService.add(userId, serviceName, subscription);
    }

    @DeleteMapping
    public Subscription remove(
            @RequestParam final String serviceName,
            @RequestBody final Subscription subscription,
            @AuthenticationPrincipal String userId
    ) {
        return subscriptionService.remove(userId, serviceName, subscription);
    }

}
