package com.nao4j.subtrol.currencyrate.service;

import com.nao4j.subtrol.currencyrate.document.CurrencyRates;
import com.nao4j.subtrol.currencyrate.dto.CurrencyRate;
import org.springframework.security.access.annotation.Secured;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

/**
 * Сервис для работы с валютами.
 */
public interface CurrencyService {

    /**
     * Возвращает список всех поддерживаемых валют.
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Set<String> getAll();

    /**
     * Возвращает курс обмена.
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    CurrencyRate getRate(String source, String target);

    /**
     * Загружает данные из источника.
     */
    @Secured("ROLE_ADMIN")
    Optional<CurrencyRates> loadData();

    /**
     * Удаляет записи старше указанной даты.
     *
     * @return количество удаленных записей
     */
    @Secured("ROLE_ADMIN")
    long deleteOldest(LocalDate date);

}
