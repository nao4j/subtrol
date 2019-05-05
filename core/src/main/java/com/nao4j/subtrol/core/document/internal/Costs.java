package com.nao4j.subtrol.core.document.internal;

import com.nao4j.subtrol.currencyrate.dto.CurrencyRate;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

@Data
public class Costs {

    @NonNull
    private final Price total;

    private final boolean isAccurate;
    @NonNull
    private final ExactPeriod period;
    @NonNull
    private final Set<CurrencyRate> currencyRates;
    @NonNull
    private final List<Cost> costs;

}
