package com.nao4j.subtrol.service

import com.nao4j.subtrol.model.CurrencyRate
import com.nao4j.subtrol.model.CurrencyRates
import java.time.LocalDate

/**
 * Сервис для работы с валютами.
 */
interface CurrencyService {

    /**
     * Возвращает список всех поддерживаемых валют.
     */
    fun getAll(): Set<String>

    /**
     * Возвращает курс обмена.
     */
    fun getRate(source: String, target: String): CurrencyRate

    /**
     * Загружает данные из источника.
     */
    fun loadData(): CurrencyRates?

    /**
     * Удаляет записи старше указанной даты.
     *
     * @return количество удаленных записей
     */
    fun deleteOldest(date: LocalDate): Long

}
