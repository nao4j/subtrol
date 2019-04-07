package com.nao4j.subtrol.controller;

import com.nao4j.subtrol.document.internal.Subscription;
import com.nao4j.subtrol.dto.UserCredentials;
import com.nao4j.subtrol.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<Subscription> getAll(@RequestParam final String serviceName) {
        final var user = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return subscriptionService.getAll(user.getId(), serviceName);
    }

    @PostMapping
    private Subscription add(@RequestParam final String serviceName, @RequestBody final Subscription subscription) {
        final var user = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return subscriptionService.add(user.getId(), serviceName, subscription);
    }

    @DeleteMapping
    public Subscription remove(@RequestParam final String serviceName, @RequestBody final Subscription subscription) {
        final var user = (UserCredentials) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return subscriptionService.remove(user.getId(), serviceName, subscription);
    }

}
