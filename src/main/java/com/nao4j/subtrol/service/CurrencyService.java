package com.nao4j.subtrol.service;

import com.nao4j.subtrol.document.CurrencyRates;
import com.nao4j.subtrol.document.internal.CurrencyRate;
import org.springframework.security.access.annotation.Secured;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static com.nao4j.subtrol.security.Roles.ADMIN;
import static com.nao4j.subtrol.security.Roles.USER;

/**
 * Сервис для работы с валютами.
 */
public interface CurrencyService {

    /**
     * Возвращает список всех поддерживаемых валют.
     */
    @Secured({USER, ADMIN})
    Set<String> getAll();

    /**
     * Возвращает курс обмена.
     */
    @Secured({USER, ADMIN})
    CurrencyRate getRate(String source, String target);

    /**
     * Загружает данные из источника.
     */
    @Secured(ADMIN)
    Optional<CurrencyRates> loadData();

    /**
     * Удаляет записи старше указанной даты.
     *
     * @return количество удаленных записей
     */
    @Secured(ADMIN)
    long deleteOldest(LocalDate date);

}
