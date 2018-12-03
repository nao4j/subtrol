package com.nao4j.subtrol.service

import com.nao4j.subtrol.dto.ShortService
import com.nao4j.subtrol.model.Service
import com.nao4j.subtrol.repository.UserRepository
import java.time.LocalDateTime.now

@org.springframework.stereotype.Service
class ServiceServiceImpl(private val userRepository: UserRepository) : ServiceService {

    override fun getAllForUser(userId: String): Set<ShortService> {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException() }
        return user.services.map { service ->
            val currentSubscription = service.subscriptions.find {
                val now = now()
                it.period.start <= now && (it.period.end == null || it.period.end > now)
            }
            ShortService(service.name, currentSubscription)
        }.sortedWith(compareBy { it.name }).toSet()
    }

    override fun create(userId: String, service: ShortService): ShortService {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException() }
        if (user.services.map(Service::name).contains(service.name)) {
            throw IllegalArgumentException()
        }
        val updatedUser = user.copy(services = user.services.plus(Service(service.name)))
        userRepository.save(updatedUser)
        return service
    }

    override fun remove(userId: String, service: ShortService): ShortService {
        val user = userRepository.findById(userId).orElseThrow { IllegalArgumentException() }
        val fullService: Service = user.services.find { it.name == service.name } ?: throw IllegalArgumentException()
        val updatedUser = user.copy(services = user.services.minus(fullService))
        userRepository.save(updatedUser)
        return service
    }

}
