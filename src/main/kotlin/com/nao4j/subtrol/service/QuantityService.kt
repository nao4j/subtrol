package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.Quantity.QuantityType
import com.nao4j.subtrol.security.ADMIN
import com.nao4j.subtrol.security.USER
import org.springframework.security.access.annotation.Secured

/**
 * Сервис для работы с размерностями.
 */
interface QuantityService {

    /**
     * Получить множество поддерживаемых размерностей.
     */
    @Secured(value = [USER, ADMIN])
    fun getAll(): Set<QuantityType>

}
