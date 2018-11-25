package com.nao4j.subtrol.controller

import com.nao4j.subtrol.model.Costs
import com.nao4j.subtrol.model.ExactPeriod
import com.nao4j.subtrol.service.CostService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/costs")
class CostController(val costService: CostService) {

    @GetMapping
    fun getAllInPeriod(
            @RequestParam userId: String,
            @RequestParam @DateTimeFormat(iso = DATE_TIME) start: LocalDateTime,
            @RequestParam @DateTimeFormat(iso = DATE_TIME) end: LocalDateTime
    ): Costs = costService.calculateForPeriod(userId, ExactPeriod(start, end))

}
