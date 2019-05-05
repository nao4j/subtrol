package com.nao4j.subtrol.currencyrate.dto;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class CurrencyRate {

    @NonNull
    private final String source;
    @NonNull
    private final String target;
    @NonNull
    private final BigDecimal rate;

}
