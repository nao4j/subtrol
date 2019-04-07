package com.nao4j.subtrol.service;

import com.nao4j.subtrol.document.internal.Quantity.QuantityType;
import org.springframework.security.access.annotation.Secured;

import java.util.Set;

import static com.nao4j.subtrol.security.Roles.ADMIN;
import static com.nao4j.subtrol.security.Roles.USER;

/**
 * Сервис для работы с размерностями.
 */
public interface QuantityService {

    /**
     * Получить множество поддерживаемых размерностей.
     */
    @Secured({USER, ADMIN})
    Set<QuantityType> getAll();

}
