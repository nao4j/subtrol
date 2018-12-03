package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.Service
import com.nao4j.subtrol.model.Subscription
import com.nao4j.subtrol.repository.UserRepository

@org.springframework.stereotype.Service
class SubscriptionServiceImpl(
        private val userRepository: UserRepository,
        private val currencyService: CurrencyService
): SubscriptionService {

    override fun getAll(userId: String, serviceName: String): List<Subscription> {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException() }
        val service: Service
                = user.services.find { service -> service.name == serviceName } ?:throw IllegalAccessException()
        return service.subscriptions.sortedWith(compareBy {it.period.start})
    }

    override fun add(userId: String, serviceName: String, subscription: Subscription): Subscription {
        val currencies = currencyService.getAll()
        if (!currencies.contains(subscription.price.currencyCode)) {
            throw IllegalArgumentException()
        }
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException() }
        val service: Service
                = user.services.find { service -> service.name == serviceName } ?:throw IllegalAccessException()

        val intersections: List<Subscription> = service.subscriptions.filter {
            it.period.end != null && subscription.period.start < it.period.end
        }
        if (intersections.isNotEmpty()) {
            throw IllegalArgumentException()
        }

        var newSubscriptions: List<Subscription> = service.subscriptions.toList()
        val previous: Subscription? = service.subscriptions.find { it.period.end == null }
        if (subscription == previous) {
            throw IllegalArgumentException()
        }
        if (previous != null) {
            val updatedPrevious = previous.copy(period = previous.period.copy(end = subscription.period.start))
            newSubscriptions = newSubscriptions.minus(previous).plus(updatedPrevious)
        }
        newSubscriptions = newSubscriptions.plus(subscription)

        val updatedService = service.copy(subscriptions = newSubscriptions)
        val updatedUser = user.copy(services = user.services.minus(service).plus(updatedService))
        userRepository.save(updatedUser)
        return subscription
    }

    override fun remove(userId: String, serviceName: String, subscription: Subscription): Subscription {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException() }
        val service: Service
                = user.services.find { service -> service.name == serviceName } ?:throw IllegalAccessException()
        if (!service.subscriptions.contains(subscription)) {
            throw IllegalArgumentException()
        }
        val updatedService = service.copy(subscriptions = service.subscriptions.minus(subscription))
        userRepository.save(user.copy(services = user.services.minus(service).plus(updatedService)))
        return subscription
    }

}
