package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.CurrencyRate
import com.nao4j.subtrol.model.CurrencyRates
import com.nao4j.subtrol.security.ADMIN
import com.nao4j.subtrol.security.USER
import org.springframework.security.access.annotation.Secured
import java.time.LocalDate

/**
 * Сервис для работы с валютами.
 */
interface CurrencyService {

    /**
     * Возвращает список всех поддерживаемых валют.
     */
    @Secured(value = [USER, ADMIN])
    fun getAll(): Set<String>

    /**
     * Возвращает курс обмена.
     */
    @Secured(value = [USER, ADMIN])
    fun getRate(source: String, target: String): CurrencyRate

    /**
     * Загружает данные из источника.
     */
    @Secured(ADMIN)
    fun loadData(): CurrencyRates?

    /**
     * Удаляет записи старше указанной даты.
     *
     * @return количество удаленных записей
     */
    @Secured(ADMIN)
    fun deleteOldest(date: LocalDate): Long

}
