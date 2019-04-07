package com.nao4j.subtrol.document.internal;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Wither;

import static com.nao4j.subtrol.document.internal.Quantity.QuantityType.UNITS;

@Data
@Wither
public class Subscription {

    @NonNull
    private final Price price;
    @NonNull
    private final RightOpenPeriod period;
    @NonNull
    private final Quantity quantity;

    public Subscription(final Price price, final RightOpenPeriod period, final Quantity quantity) {
        this.price = price;
        this.period = period;
        this.quantity = quantity;

        if (quantity.getType() == UNITS && period.getEnd() == null) {
            throw new IllegalArgumentException();
        }
    }
}
