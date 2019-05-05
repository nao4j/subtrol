package com.nao4j.subtrol.currencyrate.scheduler;

import com.nao4j.subtrol.currencyrate.provider.CurrencyRatesProvider;
import com.nao4j.subtrol.currencyrate.repository.CurrencyRatesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyRateUpdateScheduler {

    private final CurrencyRatesRepository cacheRepository;
    private final CurrencyRatesProvider currencyRatesProvider;

    @Scheduled(cron = "0 0 3 1/1 * ?")
    public void update() {
        final var currencyRates = currencyRatesProvider.latest().map(cacheRepository::save);
        if (currencyRates.isPresent()) {
            final var currenciesCount = currencyRates.get().getRates().size();
            final var base = currencyRates.get().getBase();
            log.info("Update rates for {} currencies by base {}", currenciesCount, base);
        } else {
            log.error("Could not update currency rates");
        }
    }

}
