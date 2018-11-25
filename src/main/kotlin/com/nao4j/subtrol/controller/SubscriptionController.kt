package com.nao4j.subtrol.controller

import com.nao4j.subtrol.model.Subscription
import com.nao4j.subtrol.service.SubscriptionService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subscriptions")
class SubscriptionController(val subscriptionService: SubscriptionService) {

    @GetMapping
    fun getAll(@RequestParam userId: String, @RequestParam serviceName: String): List<Subscription>
            = subscriptionService.getAll(userId, serviceName)

    @PostMapping
    fun add(
            @RequestParam userId: String,
            @RequestParam serviceName: String,
            @RequestBody subscription: Subscription
    ): Subscription = subscriptionService.add(userId, serviceName, subscription)

    @DeleteMapping
    fun remove(
            @RequestParam userId: String,
            @RequestParam serviceName: String,
            @RequestBody subscription: Subscription
    ): Subscription = subscriptionService.remove(userId, serviceName, subscription)

}
