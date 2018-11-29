package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.Costs
import com.nao4j.subtrol.model.ExactPeriod
import com.nao4j.subtrol.security.ADMIN
import com.nao4j.subtrol.security.USER
import org.springframework.security.access.annotation.Secured

interface CostService {

    @Secured(value = [USER, ADMIN])
    fun calculateForPeriod(userId: String, period: ExactPeriod): Costs

}
