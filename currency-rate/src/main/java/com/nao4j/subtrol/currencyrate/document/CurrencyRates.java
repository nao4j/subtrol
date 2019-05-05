package com.nao4j.subtrol.currencyrate.document;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Data
@Document(collection = "currency_rates")
public class CurrencyRates {

    @Id
    private String id;
    private final String base;
    private final LocalDate date;
    private final Instant timestamp;
    private final boolean success;
    private final Map<String, BigDecimal> rates;

    public CurrencyRates(
            final String id,
            @NonNull final String base,
            @NonNull final LocalDate date,
            @NonNull final Instant timestamp,
            final boolean success,
            final Map<String, BigDecimal> rates
    ) {
        this.id = id;
        this.base = base;
        this.date = date;
        this.timestamp = timestamp;
        this.success = success;
        this.rates = rates != null ? rates : emptyMap();
    }

}
