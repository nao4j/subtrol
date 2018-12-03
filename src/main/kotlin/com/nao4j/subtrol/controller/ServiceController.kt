package com.nao4j.subtrol.controller

import com.nao4j.subtrol.dto.ShortService
import com.nao4j.subtrol.dto.UserCredentials
import com.nao4j.subtrol.service.ServiceService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/services")
class ServiceController(private val serviceService: ServiceService) {

    @GetMapping
    fun getAll(): Set<ShortService> {
        val user = SecurityContextHolder.getContext().authentication.details as UserCredentials
        return serviceService.getAllForUser(user.id)
    }

    @PostMapping
    fun create(@RequestBody service: ShortService): ShortService {
        val user = SecurityContextHolder.getContext().authentication.details as UserCredentials
        return serviceService.create(user.id, service)
    }

    @DeleteMapping
    fun remove(@RequestBody service: ShortService): ShortService {
        val user = SecurityContextHolder.getContext().authentication.details as UserCredentials
        return serviceService.remove(user.id, service)
    }

}
