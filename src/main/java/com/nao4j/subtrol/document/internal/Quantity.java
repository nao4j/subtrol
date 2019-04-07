package com.nao4j.subtrol.document.internal;

import lombok.Data;
import lombok.NonNull;

@Data
public class Quantity {

    public enum QuantityType {
        UNITS,
        HOURS,
        DAYS,
        MONTHS,
        YEARS
    }

    private final long count;
    @NonNull
    private final QuantityType type;

    public Quantity(final long count, final QuantityType type) {
        this.count = count;
        this.type = type;

        if (count <= 0) {
            throw new IllegalArgumentException();
        }
    }

}
