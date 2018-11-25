package com.nao4j.subtrol.controller

import com.nao4j.subtrol.dto.ShortService
import com.nao4j.subtrol.service.ServiceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/services")
class ServiceController(val serviceService: ServiceService) {

    @GetMapping
    fun getAll(@RequestParam userId: String): Set<ShortService> = serviceService.getAllForUser(userId)

    @PostMapping
    fun create(@RequestParam userId: String, @RequestBody service: ShortService): ShortService
            = serviceService.create(userId, service)

}
