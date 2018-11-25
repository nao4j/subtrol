package com.nao4j.subtrol.controller

import com.nao4j.subtrol.service.CurrencyService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/currencies")
class CurrencyController(val currencyService: CurrencyService) {

    @GetMapping
    fun getAll() = currencyService.getAll()

    @PostMapping
    fun loadData() = currencyService.loadData()

    @DeleteMapping
    fun deleteOld() = currencyService.deleteOldest(LocalDate.now().minusDays(7))

}
