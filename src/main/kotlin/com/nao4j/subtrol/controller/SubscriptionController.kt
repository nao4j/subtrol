package com.nao4j.subtrol.controller

import com.nao4j.subtrol.dto.UserCredentials
import com.nao4j.subtrol.model.Subscription
import com.nao4j.subtrol.service.SubscriptionService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/subscriptions")
class SubscriptionController(val subscriptionService: SubscriptionService) {

    @GetMapping
    fun getAll(@RequestParam serviceName: String): List<Subscription> {
        val user = SecurityContextHolder.getContext().authentication.details as UserCredentials
        return subscriptionService.getAll(user.id, serviceName)
    }

    @PostMapping
    fun add(@RequestParam serviceName: String, @RequestBody subscription: Subscription): Subscription {
        val user = SecurityContextHolder.getContext().authentication.details as UserCredentials
        return subscriptionService.add(user.id, serviceName, subscription)
    }

    @DeleteMapping
    fun remove(@RequestParam serviceName: String, @RequestBody subscription: Subscription): Subscription {
        val user = SecurityContextHolder.getContext().authentication.details as UserCredentials
        return subscriptionService.remove(user.id, serviceName, subscription)
    }

}
