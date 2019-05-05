package com.nao4j.subtrol.currencyrate.service;

import com.nao4j.subtrol.currencyrate.document.CurrencyRates;
import com.nao4j.subtrol.currencyrate.dto.CurrencyRate;
import com.nao4j.subtrol.currencyrate.provider.CurrencyRatesProvider;
import com.nao4j.subtrol.currencyrate.repository.CurrencyRatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toCollection;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRatesRepository cacheRepository;
    private final CurrencyRatesProvider currencyRatesProvider;

    @Override
    public Set<String> getAll() {
        return cacheRepository.findAll().stream()
                .flatMap(currencyRates -> currencyRates.getRates().keySet().stream())
                .collect(toCollection(TreeSet::new));
    }

    @Override
    public CurrencyRate getRate(final String source, final String target) {
        final var fixerData = cacheRepository.findTopByOrderByTimestampDesc()
                .orElseThrow(IllegalArgumentException::new);
        if (source.equals(fixerData.getBase())) {
            return new CurrencyRate(source, target, fixerData.getRates().get(target));
        } else if (target.equals(fixerData.getBase())) {
            return new CurrencyRate(source, target, ONE.divide(fixerData.getRates().get(target), 6, HALF_UP));
        }
        final var sourceRate = fixerData.getRates().get(source);
        final var targetRate = fixerData.getRates().get(target);
        return new CurrencyRate(source, target, targetRate.divide(sourceRate, 6, HALF_UP));
    }

    @Override
    public Optional<CurrencyRates> loadData() {
        return currencyRatesProvider.latest().map(cacheRepository::save);
    }

    @Override
    public long deleteOldest(final LocalDate date) {
        return cacheRepository.deleteAllByDateBefore(date);
    }

}
