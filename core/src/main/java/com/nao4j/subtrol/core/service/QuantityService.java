package com.nao4j.subtrol.core.service;

import com.nao4j.subtrol.core.document.internal.Quantity;
import org.springframework.security.access.annotation.Secured;

import java.util.Set;

/**
 * Сервис для работы с размерностями.
 */
public interface QuantityService {

    /**
     * Получить множество поддерживаемых размерностей.
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Set<Quantity.QuantityType> getAll();

}
