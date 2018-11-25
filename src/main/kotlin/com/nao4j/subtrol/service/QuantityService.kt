package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.Quantity.QuantityType

/**
 * Сервис для работы с размерностями.
 */
interface QuantityService {

    /**
     * Получить множество поддерживаемых размерностей.
     */
    fun getAll(): Set<QuantityType>

}
