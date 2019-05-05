package com.nao4j.subtrol.core.document.internal;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Data
public class Price {

    @NonNull
    private final BigDecimal sum;
    @NonNull
    private final String currencyCode;

    public Price(final BigDecimal sum, final String currencyCode) {
        this.sum = sum;
        this.currencyCode = currencyCode;

        if (sum.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException();   // todo: message
        }
    }

}
