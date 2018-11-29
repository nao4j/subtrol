package com.nao4j.subtrol.controller

import com.nao4j.subtrol.model.Quantity.QuantityType
import com.nao4j.subtrol.service.QuantityService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/quantities")
class QuantityController(val quantityService: QuantityService) {

    @GetMapping
    fun getAll(): Set<QuantityType> = quantityService.getAll()

}
