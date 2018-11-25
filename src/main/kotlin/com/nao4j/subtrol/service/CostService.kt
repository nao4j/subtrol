package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.Costs
import com.nao4j.subtrol.model.ExactPeriod

interface CostService {

    fun calculateForPeriod(userId: String, period: ExactPeriod): Costs

}
