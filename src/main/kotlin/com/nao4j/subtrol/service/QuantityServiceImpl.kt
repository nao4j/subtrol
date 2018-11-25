package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.Quantity.QuantityType
import com.nao4j.subtrol.model.Quantity.QuantityType.MONTHS
import org.springframework.stereotype.Service

@Service
class QuantityServiceImpl: QuantityService {

    override fun getAll(): Set<QuantityType> = setOf(MONTHS)

}
